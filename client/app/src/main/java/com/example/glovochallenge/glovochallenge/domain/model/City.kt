package com.example.glovochallenge.glovochallenge.domain.model

data class City(
    val code: String,
    val name: String,
    val workingArea: List<String>,
    val countryCode: String,
    val cityInfo: CityInfo?
)
