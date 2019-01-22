package com.example.glovochallenge.glovochallenge.presentation.citysearch

import com.example.glovochallenge.glovochallenge.core.scheduler.SchedulerFactory
import com.example.glovochallenge.glovochallenge.domain.interactor.CityCodeInteractor
import com.example.glovochallenge.glovochallenge.domain.interactor.CityGroupInteractor

class CitySearchPresenterImpl(private val cityGroupInteractor: CityGroupInteractor,
                              private val cityCodeInteractor: CityCodeInteractor,
                              private val schedulerFactory: SchedulerFactory,
                              private val citySearchView: CitySearchView) : CitySearchPresenter {
    override fun loadCityGroup() {
        cityGroupInteractor.getCityGroup()
            .subscribeOn(schedulerFactory.io())
            .observeOn(schedulerFactory.main())
            .subscribe({ cityGroupMap ->

            }, {
            })
    }

    fun pick() {
        cityCodeInteractor.saveSelectCityCode("dummycode")
        //TODO view.backToMap()
    }
}
