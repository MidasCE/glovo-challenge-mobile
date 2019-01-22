package com.example.glovochallenge.glovochallenge.presentation.citysearch

import com.example.glovochallenge.glovochallenge.core.scheduler.SchedulerFactory
import com.example.glovochallenge.glovochallenge.domain.interactor.CityCodeInteractor
import com.example.glovochallenge.glovochallenge.domain.interactor.CityGroupInteractor
import com.example.glovochallenge.glovochallenge.presentation.citysearch.model.CitySearchItem
import io.reactivex.disposables.CompositeDisposable

class CitySearchPresenterImpl(
    private val cityGroupInteractor: CityGroupInteractor,
    private val cityCodeInteractor: CityCodeInteractor,
    private val schedulerFactory: SchedulerFactory,
    private val citySearchView: CitySearchView
) : CitySearchPresenter {

    private var compositeDisposable = CompositeDisposable()

    override fun loadCityGroup() {
        val disposable = cityGroupInteractor.getCityGroup()
            .subscribeOn(schedulerFactory.io())
            .observeOn(schedulerFactory.main())
            .subscribe({ cityGroupMap ->
                val itemsList = mutableListOf<CitySearchItem>()
                for ((country, cityList) in cityGroupMap) {
                    val countryItem = CitySearchItem.CountryItem(country.name)
                    itemsList.add(countryItem)
                    for (city in cityList) {
                        val cityItem = CitySearchItem.CityItem(city.name, city.code)
                        itemsList.add(cityItem)
                    }
                }
                citySearchView.showCityGroup(itemsList)
            }, {
            })
        compositeDisposable.add(disposable)
    }

    override fun onActivityDestroy() {
        compositeDisposable.clear()
    }

    override fun saveSelectCityCode(cityCode: String) {
        cityCodeInteractor.saveSelectCityCode(cityCode)
        citySearchView.navigateBackTomapView()
    }
}
