package com.example.glovochallenge.glovochallenge.presentation.main

import com.example.glovochallenge.glovochallenge.presentation.main.model.CityDetailViewModel
import com.example.glovochallenge.glovochallenge.presentation.main.model.WorkingAreaViewModel
import com.google.android.gms.maps.model.LatLngBounds

interface MapInfoView {

    fun setMapLocation(latLngBounds: LatLngBounds)

    fun updateCityDetailInformation(cityDetailViewModel: CityDetailViewModel)

    fun generateWorkingArea(cities: List<WorkingAreaViewModel>)

    fun navigateToPermissionSettings()

    fun navigateToCitySearch()
}
