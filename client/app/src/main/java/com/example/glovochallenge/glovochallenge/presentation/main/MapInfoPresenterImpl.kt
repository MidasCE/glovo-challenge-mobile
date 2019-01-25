package com.example.glovochallenge.glovochallenge.presentation.main

import android.util.Log
import com.example.glovochallenge.glovochallenge.core.Mapper
import com.example.glovochallenge.glovochallenge.core.scheduler.SchedulerFactory
import com.example.glovochallenge.glovochallenge.domain.interactor.CityCodeInteractor
import com.example.glovochallenge.glovochallenge.domain.interactor.CityInfoInteractor
import com.example.glovochallenge.glovochallenge.domain.interactor.LocationInteractor
import com.example.glovochallenge.glovochallenge.domain.model.City
import com.example.glovochallenge.glovochallenge.presentation.main.model.CityViewModel
import com.google.android.gms.maps.model.LatLng
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.zipWith

class MapInfoPresenterImpl(
    private val locationInteractor: LocationInteractor,
    private val cityInfoInteractor: CityInfoInteractor,
    private val cityCodeInteractor: CityCodeInteractor,
    private val schedulerFactory: SchedulerFactory,
    private val cityViewModelMapper : Mapper<City, CityViewModel?>,
    private val view: MapInfoView
) : MapInfoPresenter {
    private var compositeDisposable = CompositeDisposable()

    override fun firstLoad() {
        if (locationInteractor.isPermissionGranted()) {
            val disposable = cityInfoInteractor.getCityList().zipWith(locationInteractor.getCurrentLocation())
                .subscribeOn(schedulerFactory.io())
                .observeOn(schedulerFactory.main())
                .subscribe({ pair ->
                    val cityViewModelList = pair.first.mapNotNull { cityViewModelMapper.map(it) }
                    val cityViewModel =
                        findCityForLocation(cityViewModelList, LatLng(pair.second.latitude, pair.second.longitude))

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
                val viewModel = cityViewModelMapper.map(it)
                if (viewModel != null) {
                    view.setMapLocation(viewModel)
                    view.updateCityDetailInformation(viewModel)
                }
            }, {
            })
        compositeDisposable.add(disposable)
    }

    private fun findCityForLocation(cityViewModels : List<CityViewModel>, latLng : LatLng) : CityViewModel? =
        cityViewModels.find {
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
