package com.example.glovochallenge.glovochallenge.presentation.main

import com.example.glovochallenge.glovochallenge.domain.interactor.LocationPermissionInteractor

class MapInfoPresenterImpl(
    private val locationPermissionInteractor: LocationPermissionInteractor
) : MapInfoPresenter {

    override fun onReceivedLocationPermissionResponse(isGranted: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
