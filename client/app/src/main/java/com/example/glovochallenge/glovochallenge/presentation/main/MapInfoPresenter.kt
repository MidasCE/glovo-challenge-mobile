package com.example.glovochallenge.glovochallenge.presentation.main

import com.google.android.gms.maps.model.LatLngBounds

interface MapInfoPresenter {

    fun firstLoad()

    fun onMarkerClick(name: String)

    fun handleWorkingArea(isZoomIn: Boolean, latLngBounds: LatLngBounds)

    fun loadCityDetail(isFirstLoadDetail: Boolean = false)

    fun onReceivedLocationPermissionResponse(isGranted: Boolean)

    fun onActivityDestroy()
}
