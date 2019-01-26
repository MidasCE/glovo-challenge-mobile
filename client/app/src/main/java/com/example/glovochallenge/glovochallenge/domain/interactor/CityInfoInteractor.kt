package com.example.glovochallenge.glovochallenge.domain.interactor

import com.example.glovochallenge.glovochallenge.domain.model.City
import com.example.glovochallenge.glovochallenge.domain.model.Country
import io.reactivex.Single

interface CityInfoInteractor {

    fun getCachedCityList(): List<City>

    fun getCityList(): Single<List<City>>

    fun getCityDetail(): Single<City>
}