package com.example.glovochallenge.glovochallenge.presentation.main

interface MapInfoPresenter {

    fun firstLoad()

    fun updateCityDetail()

    fun onReceivedLocationPermissionResponse(isGranted: Boolean)

    fun onActivityDestroy()
}
