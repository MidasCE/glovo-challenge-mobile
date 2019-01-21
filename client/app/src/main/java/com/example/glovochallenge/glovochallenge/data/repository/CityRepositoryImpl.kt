package com.example.glovochallenge.glovochallenge.data.repository

import com.example.glovochallenge.glovochallenge.data.api.NetworkAPI
import com.example.glovochallenge.glovochallenge.data.model.CityDetailNetworkModel
import com.example.glovochallenge.glovochallenge.data.model.CityInfoNetworkModel
import io.reactivex.Single

class CityRepositoryImpl(private val networkAPI: NetworkAPI) : CityRepository {

    override fun getCityList(): Single<List<CityDetailNetworkModel>> = networkAPI.getCityList()

    override fun getCityDetail(cityCode: String): Single<List<CityInfoNetworkModel>>
            = networkAPI.getCityDetail(cityCode)
}
