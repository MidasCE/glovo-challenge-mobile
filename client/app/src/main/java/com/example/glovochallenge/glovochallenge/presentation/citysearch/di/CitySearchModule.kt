package com.example.glovochallenge.glovochallenge.presentation.citysearch.di

import com.example.glovochallenge.glovochallenge.core.scheduler.SchedulerFactory
import com.example.glovochallenge.glovochallenge.domain.interactor.CityCodeInteractor
import com.example.glovochallenge.glovochallenge.domain.interactor.CountryGroupInteractor
import com.example.glovochallenge.glovochallenge.presentation.citysearch.CitySearchActivity
import com.example.glovochallenge.glovochallenge.presentation.citysearch.CitySearchPresenter
import com.example.glovochallenge.glovochallenge.presentation.citysearch.CitySearchPresenterImpl
import com.example.glovochallenge.glovochallenge.presentation.citysearch.CitySearchView
import com.example.glovochallenge.glovochallenge.presentation.citysearch.list.CitySearchAdapter
import dagger.Module
import dagger.Provides

@Module
class CitySearchModule {

    @Provides
    fun provideCitySearchView(citySearchActivity: CitySearchActivity): CitySearchView = citySearchActivity

    @Provides
    fun provideCitySearchPresenter(countryGroupInteractor: CountryGroupInteractor,
                                   cityCodeInteractor: CityCodeInteractor,
                                   schedulerFactory: SchedulerFactory,
                                   citySearchView: CitySearchView): CitySearchPresenter = CitySearchPresenterImpl(
            countryGroupInteractor, cityCodeInteractor, schedulerFactory, citySearchView)
}
