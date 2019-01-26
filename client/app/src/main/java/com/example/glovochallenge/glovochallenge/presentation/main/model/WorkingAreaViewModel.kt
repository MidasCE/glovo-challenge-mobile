package com.example.glovochallenge.glovochallenge.presentation.main.model

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

class WorkingAreaViewModel(
    val code: String,
    val name: String,
    val workingArea: List<List<LatLng>>,
    val workingBoundary: LatLngBounds
)