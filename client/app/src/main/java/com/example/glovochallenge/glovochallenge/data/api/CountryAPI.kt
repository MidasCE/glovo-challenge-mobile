package com.example.glovochallenge.glovochallenge.data.api

import com.example.glovochallenge.glovochallenge.data.model.CountryNetworkModel
import io.reactivex.Single
import retrofit2.http.GET

interface CountryAPI {

    @GET("countries")
    fun getWeatherForecast(): Single<List<CountryNetworkModel>>
}
