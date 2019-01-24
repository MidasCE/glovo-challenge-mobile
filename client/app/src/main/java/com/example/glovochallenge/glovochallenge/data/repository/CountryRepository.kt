package com.example.glovochallenge.glovochallenge.data.repository

import com.example.glovochallenge.glovochallenge.data.model.CountryNetworkModel
import io.reactivex.Single

interface CountryRepository {
    var cacheCountryList: List<CountryNetworkModel>?

    fun getCountryList() : Single<List<CountryNetworkModel>>
}