package com.example.glovochallenge.glovochallenge.presentation.main

import com.example.glovochallenge.glovochallenge.presentation.main.model.CityViewModel

interface MapInfoView {

    fun setMapLocation(city: CityViewModel)

    fun updateCityDetailInformation(city: CityViewModel)

    fun navigateToPermissionSettings()

    fun navigateToCitySearch()
}
