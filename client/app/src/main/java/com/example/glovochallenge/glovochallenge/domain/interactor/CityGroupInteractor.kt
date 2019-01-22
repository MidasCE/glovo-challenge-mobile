package com.example.glovochallenge.glovochallenge.domain.interactor

import com.example.glovochallenge.glovochallenge.domain.model.City
import com.example.glovochallenge.glovochallenge.domain.model.Country
import io.reactivex.Single

interface CityGroupInteractor {

    fun getCityGroup(): Single<HashMap<Country, List<City>>>
}