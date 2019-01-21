package com.example.glovochallenge.glovochallenge.domain.mapper

import com.example.glovochallenge.glovochallenge.core.Mapper
import com.example.glovochallenge.glovochallenge.data.model.CityDetailNetworkModel
import com.example.glovochallenge.glovochallenge.domain.model.City
import com.example.glovochallenge.glovochallenge.domain.model.CityInfo

class CityDetailMapper : Mapper<CityDetailNetworkModel, City> {
    override fun map(value: CityDetailNetworkModel) = with(value) {
        City(
            code,
            name,
            workingArea,
            countryCode,
            CityInfo(
                currency,
                enabled,
                busy,
                timeZone,
                languageCode
            )
        )
    }
}
