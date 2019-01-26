package com.example.glovochallenge.glovochallenge.presentation.main.model

data class CityDetailViewModel(
    val code: String,
    val name: String,
    val countryCode: String,
    val currency: String,
    val isEnabled: Boolean,
    val isBusy: Boolean,
    val timeZone: String,
    val languageCode: String)
