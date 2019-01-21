package com.example.glovochallenge.glovochallenge.data.repository

import com.example.glovochallenge.glovochallenge.data.model.CityDetailNetworkModel
import com.example.glovochallenge.glovochallenge.data.model.CityInfoNetworkModel
import io.reactivex.Single

interface CityRepository {

    fun getCityList(): Single<List<CityDetailNetworkModel>>

    fun getCityDetail(cityCode: String): Single<List<CityInfoNetworkModel>>
}