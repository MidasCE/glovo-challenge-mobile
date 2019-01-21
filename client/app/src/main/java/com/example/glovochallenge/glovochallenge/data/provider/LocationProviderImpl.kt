package com.example.glovochallenge.glovochallenge.data.provider

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.example.glovochallenge.glovochallenge.data.model.CoordinateEntity
import io.reactivex.Single
import java.lang.Exception

class LocationProviderImpl(private val context: Context) : LocationProvider, LocationListener {

    override fun onLocationChanged(p0: Location?) {

    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {

    }

    override fun onProviderEnabled(p0: String?) {

    }

    override fun onProviderDisabled(p0: String?) {

    }

    private val requiredPermissions =
        listOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)

    private var locationManager: LocationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as android.location.LocationManager

    @SuppressLint("MissingPermission")
    override fun getCurrentLocation(): Single<CoordinateEntity> {
        if (isPermissionGranted()) {
            if (!isLocationServiceEnabled())
                return Single.error(
                    Exception(
                        "Service Disabled"
                    )
                )

            var location: Location? = null

            if (isNetworkProviderEnabled()) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0F, this)
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            } else if (isGpsProviderEnabled()) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0F, this)
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            }

            return location?.let {
                Single.just(CoordinateEntity(it.latitude, it.longitude))
            } ?: Single.error(Exception("Can't get location"))

        } else {
            return Single.error(
                Exception(
                    "Require permissions: $requiredPermissions"
                )
            )
        }
    }

    override fun isPermissionGranted(): Boolean {
        for (permission in requiredPermissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED)
                return false
        }
        return true
    }

    private fun isLocationServiceEnabled(): Boolean {
        return isGpsProviderEnabled() || isNetworkProviderEnabled()
    }

    private fun isNetworkProviderEnabled(): Boolean {
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun isGpsProviderEnabled(): Boolean {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

}
