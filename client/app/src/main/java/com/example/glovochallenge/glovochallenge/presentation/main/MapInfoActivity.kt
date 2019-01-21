package com.example.glovochallenge.glovochallenge.presentation.main

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import com.example.glovochallenge.glovochallenge.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import javax.inject.Inject

class MapInfoActivity: Activity(), MapInfoView , OnMapReadyCallback {

    @Inject
    lateinit var presenter: MapInfoPresenter

    private lateinit var mapView : MapView

    companion object {
        const val REQUEST_CODE_ASK_PERMISSIONS = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_map)
        initView()
    }

    private fun initView() {
        mapView = findViewById(R.id.mapView)
    }

    override fun onMapReady(p0: GoogleMap?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
}
