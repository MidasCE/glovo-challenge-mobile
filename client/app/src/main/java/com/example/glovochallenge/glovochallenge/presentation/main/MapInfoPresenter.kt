package com.example.glovochallenge.glovochallenge.presentation.main

import com.google.android.gms.maps.model.LatLngBounds

interface MapInfoPresenter {

    fun firstLoad()

    fun findWorkingArea(latLngBounds: LatLngBounds)

    fun updateCityDetail()

    fun onReceivedLocationPermissionResponse(isGranted: Boolean)

    fun onActivityDestroy()
}
