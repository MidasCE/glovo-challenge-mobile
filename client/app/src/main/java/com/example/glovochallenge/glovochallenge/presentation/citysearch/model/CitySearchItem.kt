package com.example.glovochallenge.glovochallenge.presentation.citysearch.model

sealed class CitySearchItem {

    data class CountryItem(val countryName : String) : CitySearchItem()

    data class CityItem(val cityName : String,
                        val cityCode : String) : CitySearchItem()
}