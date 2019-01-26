package com.example.glovochallenge.glovochallenge.domain.mapper

import com.example.glovochallenge.glovochallenge.data.model.CountryNetworkModel
import org.amshove.kluent.`should equal`
import org.junit.Before
import org.junit.Test

class CountryMapperTest {

    private lateinit var countryMapper: CountryMapper

    @Before
    fun setUp() {
        countryMapper = CountryMapper()
    }

    @Test
    fun `Test map data`() {
        val networkModel = CountryNetworkModel("code", "name")
        val result = countryMapper.map(networkModel)

        result.code `should equal` "code"
        result.name `should equal` "name"
    }
}