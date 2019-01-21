package com.example.glovochallenge.glovochallenge.domain.interactor

import com.example.glovochallenge.glovochallenge.data.provider.LocationProvider

class LocationPermissionInteratorImpl(private val locationRepository: LocationProvider) : LocationPermissionInteractor {
    override fun isPermissionGranted() = locationRepository.isPermissionGranted()
}
