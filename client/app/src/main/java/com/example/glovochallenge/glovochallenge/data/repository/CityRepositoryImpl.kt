package com.example.glovochallenge.glovochallenge.data.repository

import com.example.glovochallenge.glovochallenge.data.api.NetworkAPI
import com.example.glovochallenge.glovochallenge.data.model.CityDetailNetworkModel
import com.example.glovochallenge.glovochallenge.data.model.CityInfoNetworkModel
import io.reactivex.Single

class CityRepositoryImpl(private val networkAPI: NetworkAPI) : CityRepository {
    override var cacheCityList: List<CityInfoNetworkModel>? = null

    override fun getCityList(): Single<List<CityInfoNetworkModel>> = networkAPI.getCityList().doOnSuccess {
        cacheCityList = it
    }

    override fun getCityDetail(cityCode: String): Single<CityDetailNetworkModel>
            = networkAPI.getCityDetail(cityCode)
}
