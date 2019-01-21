package com.example.glovochallenge.glovochallenge.data.repository

import com.example.glovochallenge.glovochallenge.data.model.CountryNetworkModel
import io.reactivex.Single

interface CountryRepository {

    fun getCountryList() : Single<List<CountryNetworkModel>>
}