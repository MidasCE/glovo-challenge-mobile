package com.example.glovochallenge.glovochallenge.data.repository

import com.example.glovochallenge.glovochallenge.data.api.NetworkAPI
import com.example.glovochallenge.glovochallenge.data.model.CountryNetworkModel
import io.reactivex.Single

class CountryRepositoryImpl(private val api: NetworkAPI) : CountryRepository {
    override var cacheCountryList: List<CountryNetworkModel>? = null

    override fun getCountryList(): Single<List<CountryNetworkModel>> = api.getCountryList()
        .doOnSuccess {
            cacheCountryList = it
        }
}
