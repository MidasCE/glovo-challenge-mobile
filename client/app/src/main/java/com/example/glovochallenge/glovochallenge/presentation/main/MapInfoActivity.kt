package com.example.glovochallenge.glovochallenge.presentation.main

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import com.example.glovochallenge.glovochallenge.R
import com.example.glovochallenge.glovochallenge.presentation.citysearch.CitySearchActivity
import com.example.glovochallenge.glovochallenge.presentation.main.model.CityViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import javax.inject.Inject

class MapInfoActivity: Activity(), MapInfoView , OnMapReadyCallback {

    @Inject
    lateinit var presenter: MapInfoPresenter

    private lateinit var mapView : GoogleMap

    companion object {
        const val REQUEST_CODE_ASK_PERMISSIONS = 123
        const val REQUEST_CODE_CITY_SEARCH = 124
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_map)
        initView()
    }

    private fun initView() {
        val mapFragment = fragmentManager.findFragmentById(R.id.mapView) as MapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mapView = googleMap
    }

    override fun navigateToPermissionSettings() {
        ActivityCompat.requestPermissions(this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            REQUEST_CODE_ASK_PERMISSIONS)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_ASK_PERMISSIONS -> {
                // If request is cancelled, the result arrays are empty.
                presenter.onReceivedLocationPermissionResponse(
                    (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                )
                return
            }
        }
    }

    override fun setMapLocation(city: CityViewModel) {
        mapView.animateCamera(CameraUpdateFactory.newLatLngBounds(city.workingBoundary, 0))
    }

    override fun updateCityDetailInformation(city: CityViewModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun navigateToCitySearch() {
        startActivityForResult(Intent(this, CitySearchActivity::class.java), REQUEST_CODE_CITY_SEARCH)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        requestCode.takeIf {
            it == REQUEST_CODE_CITY_SEARCH && resultCode == Activity.RESULT_OK
        }.let {
            presenter.updateCityDetail()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
