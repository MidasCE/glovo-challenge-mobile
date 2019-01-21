package com.example.glovochallenge.glovochallenge.data.model

import com.google.gson.annotations.SerializedName

data class CountryNetworkModel(
    @SerializedName("code") val code: String,
    @SerializedName("name") val name: String
)
