package com.example.glovochallenge.glovochallenge.domain.mapper

import com.example.glovochallenge.glovochallenge.core.Mapper
import com.example.glovochallenge.glovochallenge.data.model.CityInfoNetworkModel
import com.example.glovochallenge.glovochallenge.domain.model.City

class CityInfoMapper: Mapper<CityInfoNetworkModel, City> {
    override fun map(value: CityInfoNetworkModel) = with(value) {
        City(
            code,
            name,
            workingArea,
            countryCode,
            null
        )
    }
}