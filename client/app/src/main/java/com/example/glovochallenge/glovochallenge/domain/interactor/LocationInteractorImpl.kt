package com.example.glovochallenge.glovochallenge.domain.interactor

import com.example.glovochallenge.glovochallenge.data.model.CoordinateEntity
import com.example.glovochallenge.glovochallenge.data.provider.AppLocationProvider
import io.reactivex.Single

class LocationInteractorImpl(private val appLocationProvider: AppLocationProvider) : LocationInteractor {

    override fun isPermissionGranted() = appLocationProvider.isPermissionGranted()

    override fun getCurrentLocation(): Single<CoordinateEntity> = appLocationProvider.getCurrentLocation()
}
