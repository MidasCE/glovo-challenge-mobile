package com.example.glovochallenge.glovochallenge.presentation.citysearch

interface CitySearchPresenter {
    fun loadCityGroup()

    fun saveSelectCityCode(cityCode : String)

    fun onActivityDestroy()

    fun onBackPressed()
}
