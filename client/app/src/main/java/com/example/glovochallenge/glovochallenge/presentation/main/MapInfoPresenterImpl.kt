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
                    val cityViewModelList = pair.first.mapNotNull { workingAreaViewModelMapper.map(it) }
                    val cityViewModel =
                        findWorkAreaForLocation(cityViewModelList, LatLng(pair.second.latitude, pair.second.longitude))

                    if (cityViewModel != null) {
                        cityCodeInteractor.saveSelectCityCode(cityViewModel.code)
                        updateCityDetail()
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

    override fun updateCityDetail() {
        val disposable = cityInfoInteractor.getCityDetail()
            .subscribeOn(schedulerFactory.io())
            .observeOn(schedulerFactory.main())
            .subscribe({
                val viewModel = workingAreaViewModelMapper.map(it)
                if (viewModel != null) {
                    view.setMapLocation(viewModel.workingBoundary)
                    cityDetailViewModelMapper.map(it)?.let { detailViewModel ->
                        view.updateCityDetailInformation(detailViewModel)
                    }
                }
            }, {
            })
        compositeDisposable.add(disposable)
    }

    override fun findWorkingArea(latLngBounds: LatLngBounds) {
        cityInfoInteractor.getCachedCityList()
            .takeIf { it.isNotEmpty() }
            ?.let { list ->
                val workingAreaViewModels = list.mapNotNull { workingAreaViewModelMapper.map(it) }
                workingAreaViewModels.filter { workingArea ->
                    latLngBounds.contains(workingArea.workingBoundary.northeast) ||
                            latLngBounds.contains(workingArea.workingBoundary.southwest)
                }.takeIf { it.isNotEmpty() }
                    ?.let { workingAreas ->
                        view.generateWorkingArea(workingAreas)
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
