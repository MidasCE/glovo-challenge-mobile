package com.example.glovochallenge.glovochallenge.domain.mapper

import com.example.glovochallenge.glovochallenge.core.Mapper
import com.example.glovochallenge.glovochallenge.data.model.CountryNetworkModel
import com.example.glovochallenge.glovochallenge.domain.model.Country

class CountryMapper : Mapper<CountryNetworkModel, Country> {

    override fun map(value: CountryNetworkModel) = Country(value.code, value.name)
}
