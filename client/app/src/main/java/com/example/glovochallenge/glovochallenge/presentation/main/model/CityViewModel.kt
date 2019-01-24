package com.example.glovochallenge.glovochallenge.presentation.main.model

import com.google.android.gms.maps.model.LatLngBounds

class CityViewModel(
    val code: String,
    val name: String,
    val workingBoundary: LatLngBounds,
    val countryCode: String,
    val currency: String,
    val isEnabled: Boolean,
    val isBusy: Boolean,
    val timeZone: String,
    val languageCode: String
)