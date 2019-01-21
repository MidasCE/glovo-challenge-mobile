package com.example.glovochallenge.glovochallenge.domain.interactor

import com.example.glovochallenge.glovochallenge.data.provider.AppLocationProvider

class LocationPermissionInteratorImpl(private val locationRepository: AppLocationProvider) : LocationPermissionInteractor {
    override fun isPermissionGranted() = locationRepository.isPermissionGranted()
}
