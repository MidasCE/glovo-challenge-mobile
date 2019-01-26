package com.example.glovochallenge.glovochallenge.presentation.main

import com.example.glovochallenge.glovochallenge.presentation.main.model.CityDetailViewModel
import com.example.glovochallenge.glovochallenge.presentation.main.model.WorkingAreaViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

interface MapInfoView {

    fun setMapLocation(latLngBounds: LatLngBounds)

    fun zoomMapLocation(latLng: LatLng)

    fun updateCityDetailInformation(cityDetailViewModel: CityDetailViewModel)

    fun generateMarkerWorkingArea(workingAreaViewModels: List<WorkingAreaViewModel>)

    fun generateWorkingArea(cities: List<WorkingAreaViewModel>)

    fun navigateToPermissionSettings()

    fun navigateToCitySearch()

    fun showMessage(message: String)
}
