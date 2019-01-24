package com.example.glovochallenge.glovochallenge.presentation.citysearch

import com.example.glovochallenge.glovochallenge.core.scheduler.SchedulerFactory
import com.example.glovochallenge.glovochallenge.domain.interactor.CityCodeInteractor
import com.example.glovochallenge.glovochallenge.domain.interactor.CountryGroupInteractor
import com.example.glovochallenge.glovochallenge.presentation.citysearch.model.CitySearchItem
import io.reactivex.disposables.CompositeDisposable

class CitySearchPresenterImpl(
    private val countryGroupInteractor: CountryGroupInteractor,
    private val cityCodeInteractor: CityCodeInteractor,
    private val schedulerFactory: SchedulerFactory,
    private val citySearchView: CitySearchView
) : CitySearchPresenter {

    private var compositeDisposable = CompositeDisposable()

    override fun loadCityGroup() {
        val disposable = countryGroupInteractor.getCacheCountryWithCityGroup()
            .subscribeOn(schedulerFactory.io())
            .observeOn(schedulerFactory.main())
            .subscribe({ groupMap ->
                val itemsList = mutableListOf<CitySearchItem>()
                for ((country, cityList) in groupMap) {
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

    override fun saveSelectCityCode(cityCode: String) {
        cityCodeInteractor.saveSelectCityCode(cityCode)
        citySearchView.navigateBackTomapView()
    }

    override fun onActivityDestroy() {
        compositeDisposable.clear()
    }
}
