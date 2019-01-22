package com.example.glovochallenge.glovochallenge.presentation.citysearch

import com.example.glovochallenge.glovochallenge.presentation.citysearch.model.CitySearchItem

interface CitySearchView {

    fun showCityGroup(items: List<CitySearchItem>)
}
