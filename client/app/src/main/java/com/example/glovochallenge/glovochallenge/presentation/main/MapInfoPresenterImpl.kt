package com.example.glovochallenge.glovochallenge.presentation.main

import android.util.Log
import com.example.glovochallenge.glovochallenge.core.Mapper
import com.example.glovochallenge.glovochallenge.core.scheduler.SchedulerFactory
import com.example.glovochallenge.glovochallenge.domain.interactor.CityCodeInteractor
import com.example.glovochallenge.glovochallenge.domain.interactor.CityInfoInteractor
import com.example.glovochallenge.glovochallenge.domain.interactor.LocationInteractor
import com.example.glovochallenge.glovochallenge.domain.model.City
import com.example.glovochallenge.glovochallenge.presentation.main.model.CityDetailViewModel
import com.example.glovochallenge.glovochallenge.presentation.main.model.WorkingAreaViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.zipWith

class MapInfoPresenterImpl(
    private val locationInteractor: LocationInteractor,
    private val cityInfoInteractor: CityInfoInteractor,
    private val cityCodeInteractor: CityCodeInteractor,
    private val schedulerFactory: SchedulerFactory,
    private val workingAreaViewModelMapper: Mapper<City, WorkingAreaViewModel?>,
    private val cityDetailViewModelMapper: Mapper<City, CityDetailViewModel?>,
    private val view: MapInfoView
) : MapInfoPresenter {

    private var compositeDisposable = CompositeDisposable()

    override fun firstLoad() {
        if (locationInteractor.isPermissionGranted()) {
            val disposable = cityInfoInteractor.getCityList().zipWith(locationInteractor.getCurrentLocation())
                .subscribeOn(schedulerFactory.io())
                .observeOn(schedulerFactory.main())
                .subscribe({ pair ->
                    val workingAreaViewModels = pair.first.mapNotNull { workingAreaViewModelMapper.map(it) }
                    val workingAreaViewModel =
                        findWorkAreaForLocation(
                            workingAreaViewModels,
                            LatLng(pair.second.latitude, pair.second.longitude)
                        )
                    if (workingAreaViewModel != null) {
                        cityCodeInteractor.saveSelectCityCode(workingAreaViewModel.code)
                        view.setMapLocation(workingAreaViewModel.workingBoundary)
                    } else {
                        view.navigateToCitySearch()
                    }
                }, {
                    Log.e("error", "some error", it)
                })
            compositeDisposable.add(disposable)
        } else {
            view.navigateToPermissionSettings()
        }
    }

    override fun handleWorkingArea(isZoomIn: Boolean, latLngBounds: LatLngBounds) {
        cityInfoInteractor.getCachedCityList()
            .takeIf { it.isNotEmpty() }
            ?.asSequence()?.mapNotNull {
                workingAreaViewModelMapper.map(it)
            }
            ?.filter { workingArea ->
                latLngBounds.contains(workingArea.workingBoundary.center)
            }
            ?.toList()
            ?.takeIf { it.isNotEmpty() }
            ?.let { workingAreas ->
                if (isZoomIn) {
                    view.generateMarkerWorkingArea(workingAreas)
                } else {
                    view.generateWorkingArea(workingAreas)
                }
                val area = findWorkAreaForLocation(
                    workingAreas,
                    LatLng(latLngBounds.center.latitude, latLngBounds.center.longitude)
                )
                area?.let {
                    cityCodeInteractor.saveSelectCityCode(it.code)
                    loadCityDetail()
                }
            }
    }

    override fun loadCityDetail(isFirstLoadDetail: Boolean) {
        val disposable = cityInfoInteractor.getCityDetail()
            .subscribeOn(schedulerFactory.io())
            .observeOn(schedulerFactory.main())
            .subscribe(
                {
                    cityDetailViewModelMapper.map(it)?.let { detailViewModel ->
                        view.updateCityDetailInformation(detailViewModel)
                        if (isFirstLoadDetail) {
                            workingAreaViewModelMapper.map(it)?.let { area ->
                                view.setMapLocation(area.workingBoundary)
                            }
                        }
                    }
                }, {
                })
        compositeDisposable.add(disposable)
    }

    override fun onMarkerClick(name: String) {
        cityInfoInteractor.getCachedCityList()
            .takeIf { it.isNotEmpty() }
            ?.let { list ->
                list.first {
                    it.name == name
                }.let {
                    val viewModel = workingAreaViewModelMapper.map(it)
                    if (viewModel != null) {
                        cityCodeInteractor.saveSelectCityCode(viewModel.code)
                        loadCityDetail()
                        view.zoomMapLocation(viewModel.workingBoundary.center)
                    }
                }
            }
    }

    private fun findWorkAreaForLocation(workingAreaViewModels: List<WorkingAreaViewModel>, latLng: LatLng)
            : WorkingAreaViewModel? =
        workingAreaViewModels.find {
            it.workingBoundary.contains(latLng)
        }

    override fun onReceivedLocationPermissionResponse(isGranted: Boolean) {
        if (isGranted) {
            firstLoad()
        } else {
            view.navigateToCitySearch()
        }
    }

    override fun onActivityDestroy() {
        compositeDisposable.clear()
    }

}
