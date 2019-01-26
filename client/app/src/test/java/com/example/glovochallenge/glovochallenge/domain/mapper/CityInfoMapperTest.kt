package com.example.glovochallenge.glovochallenge.domain.mapper

import com.example.glovochallenge.glovochallenge.data.model.CityDetailNetworkModel
import com.example.glovochallenge.glovochallenge.data.model.CityInfoNetworkModel
import org.amshove.kluent.`should equal`
import org.junit.Before
import org.junit.Test

class CityInfoMapperTest {

    private lateinit var cityInfoMapper: CityInfoMapper

    @Before
    fun setUp() {
        cityInfoMapper = CityInfoMapper()
    }

    @Test
    fun `Test map data`() {
        val networkModel = CityInfoNetworkModel(
            emptyList(), "code", "name", "countryCode"
        )
        val result = cityInfoMapper.map(networkModel)

        result.code `should equal` "code"
        result.name `should equal` "name"
        result.countryCode `should equal` "countryCode"
        result.workingArea `should equal` emptyList()
        result.cityInfo `should equal` null
    }
}