package com.example.glovochallenge.glovochallenge.data.model

import com.google.gson.annotations.SerializedName

data class CityInfoNetworkModel(
    @SerializedName("working_area") val workingArea: List<String>,
    @SerializedName("code") val code: String,
    @SerializedName("name") val name: String,
    @SerializedName("country_code") val countryCode: String
)
