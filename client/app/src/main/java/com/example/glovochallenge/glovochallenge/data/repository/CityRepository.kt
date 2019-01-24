package com.example.glovochallenge.glovochallenge.data.repository

import com.example.glovochallenge.glovochallenge.data.model.CityDetailNetworkModel
import com.example.glovochallenge.glovochallenge.data.model.CityInfoNetworkModel
import io.reactivex.Single

interface CityRepository {
    var cacheCityList: List<CityInfoNetworkModel>?

    fun getCityList(): Single<List<CityInfoNetworkModel>>

    fun getCityDetail(cityCode: String): Single<CityDetailNetworkModel>
}