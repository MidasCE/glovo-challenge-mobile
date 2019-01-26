package com.example.glovochallenge.glovochallenge.presentation.main

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import com.example.glovochallenge.glovochallenge.R
import com.example.glovochallenge.glovochallenge.presentation.citysearch.CitySearchActivity
import com.example.glovochallenge.glovochallenge.presentation.main.model.CityDetailViewModel
import com.example.glovochallenge.glovochallenge.presentation.main.model.WorkingAreaViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import javax.inject.Inject
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import dagger.android.AndroidInjection


class MapInfoActivity : FragmentActivity(), MapInfoView, OnMapReadyCallback {

    @Inject
    lateinit var presenter: MapInfoPresenter

    private lateinit var mapView: GoogleMap
    private lateinit var cityNameTextView: TextView
    private lateinit var currencyTextView: TextView
    private lateinit var statusTextView: TextView
    private lateinit var timezoneTextView: TextView

    companion object {
        const val REQUEST_CODE_ASK_PERMISSIONS = 123
        const val REQUEST_CODE_CITY_SEARCH = 124
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_map)
        AndroidInjection.inject(this)
        initView()
    }

    private fun initView() {
        cityNameTextView = findViewById(R.id.cityNameTextView)
        currencyTextView = findViewById(R.id.currencyTextView)
        statusTextView = findViewById(R.id.statusTextView)
        timezoneTextView = findViewById(R.id.timezoneTextView)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapView)
                as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mapView = googleMap
        mapView.uiSettings.isZoomControlsEnabled = true
        mapView.setOnCameraIdleListener {
            presenter.handleWorkingArea(
                mapView.cameraPosition.zoom <= 10.0f,
                mapView.projection.visibleRegion.latLngBounds
            )
        }
        mapView.setOnMarkerClickListener {
            presenter.onMarkerClick(it.title)
            true
        }
        presenter.firstLoad()
    }

    override fun navigateToPermissionSettings() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            REQUEST_CODE_ASK_PERMISSIONS
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
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

    override fun setMapLocation(latLngBounds: LatLngBounds) {
        mapView.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 0))
    }

    override fun zoomMapLocation(latLng: LatLng) {
        mapView.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f))
    }

    @SuppressLint("SetTextI18n")
    override fun updateCityDetailInformation(cityDetailViewModel: CityDetailViewModel) {
        cityNameTextView.text = cityDetailViewModel.name
        statusTextView.setText(getStatus(cityDetailViewModel), TextView.BufferType.SPANNABLE)
        timezoneTextView.text = getString(R.string.timezone) + " " + cityDetailViewModel.timeZone
        currencyTextView.text = getString(R.string.currency) + " " + cityDetailViewModel.currency
    }

    override fun navigateToCitySearch() {
        startActivityForResult(Intent(this, CitySearchActivity::class.java), REQUEST_CODE_CITY_SEARCH)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        requestCode.takeIf {
            it == REQUEST_CODE_CITY_SEARCH && resultCode == Activity.RESULT_OK
        }.let {
            presenter.loadCityDetail(true)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun generateWorkingArea(cities: List<WorkingAreaViewModel>) {
        mapView.clear()
        cities.forEach { city ->
            city.workingArea.forEach { locationList ->
                if (locationList.isNotEmpty()) {
                    mapView.addPolygon(
                        PolygonOptions()
                            .addAll(locationList)
                            .fillColor(
                                ContextCompat.getColor(this, R.color.`colorฺArea`)
                            )
                    )
                }
            }

        }
    }

    override fun generateMarkerWorkingArea(workingAreaViewModels: List<WorkingAreaViewModel>) {
        mapView.clear()
        workingAreaViewModels.forEach {
            mapView.addMarker(
                MarkerOptions()
                    .title(it.name)
                    .position(it.workingBoundary.center)
            )
        }
    }

    private fun getStatus(cityDetailViewModel: CityDetailViewModel): Spannable {
        val color: Int
        val string: String
        if (cityDetailViewModel.isBusy) {
            string = getString(R.string.busy)
            color = R.color.colorRedPrimary
        } else {
            string = getString(R.string.available)
            color = R.color.colorGreenPrimary
        }

        val spannable = SpannableString(getString(R.string.status) + " " + string)
        spannable.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(this, color)
            ),
            getString(R.string.status).length + 1,
            getString(R.string.status).length + string.length + 1,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannable
    }
}
