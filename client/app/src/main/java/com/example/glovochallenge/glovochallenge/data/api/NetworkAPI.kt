package com.example.glovochallenge.glovochallenge.data.api

import com.example.glovochallenge.glovochallenge.data.model.CityInfoNetworkModel
import com.example.glovochallenge.glovochallenge.data.model.CityDetailNetworkModel
import com.example.glovochallenge.glovochallenge.data.model.CountryNetworkModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkAPI {

    @GET("cities")
    fun getCityList(): Single<List<CityDetailNetworkModel>>

    @GET("cities/{city_code}")
    fun getCityDetail(@Path("city_code") cityCode: String): Single<List<CityInfoNetworkModel>>

    @GET("countries")
    fun getCountryList(): Single<List<CountryNetworkModel>>

}
