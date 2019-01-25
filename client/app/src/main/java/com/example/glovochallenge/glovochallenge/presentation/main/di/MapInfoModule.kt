package com.example.glovochallenge.glovochallenge.presentation.main.di

import com.example.glovochallenge.glovochallenge.core.Mapper
import com.example.glovochallenge.glovochallenge.core.scheduler.SchedulerFactory
import com.example.glovochallenge.glovochallenge.domain.interactor.CityCodeInteractor
import com.example.glovochallenge.glovochallenge.domain.interactor.CityInfoInteractor
import com.example.glovochallenge.glovochallenge.domain.interactor.LocationInteractor
import com.example.glovochallenge.glovochallenge.domain.model.City
import com.example.glovochallenge.glovochallenge.presentation.main.MapInfoActivity
import com.example.glovochallenge.glovochallenge.presentation.main.MapInfoPresenter
import com.example.glovochallenge.glovochallenge.presentation.main.MapInfoPresenterImpl
import com.example.glovochallenge.glovochallenge.presentation.main.MapInfoView
import com.example.glovochallenge.glovochallenge.presentation.main.mapper.CityViewModelMapper
import com.example.glovochallenge.glovochallenge.presentation.main.model.CityViewModel
import dagger.Module
import dagger.Provides

@Module
class MapInfoModule{

    @Provides
    fun provideMapView(mapActivity: MapInfoActivity) : MapInfoView = mapActivity

    @Provides
    fun providecityViewModelMapper() : Mapper<City, CityViewModel?> = CityViewModelMapper()

    @Provides
    fun provideMapInfoPresenter(locationInteractor: LocationInteractor,
                                cityInfoInteractor: CityInfoInteractor,
                                cityCodeInteractor: CityCodeInteractor,
                                schedulerFactory: SchedulerFactory,
                                cityViewModelMapper : Mapper<City, CityViewModel?>,
                                view: MapInfoView): MapInfoPresenter
            = MapInfoPresenterImpl(locationInteractor, cityInfoInteractor, cityCodeInteractor,
            schedulerFactory, cityViewModelMapper, view)
}
