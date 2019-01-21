package com.example.glovochallenge.glovochallenge.domain.interactor

interface CityCodeInteractor {
    fun getSelectCityCode() : String

    fun saveSelectCityCode(cityCode : String)
}
