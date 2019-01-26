package com.example.glovochallenge.glovochallenge.domain.mapper

import com.example.glovochallenge.glovochallenge.data.model.CityDetailNetworkModel
import org.amshove.kluent.`should equal`
import org.junit.Before
import org.junit.Test

class CityDetailMapperTest {

    private lateinit var cityDetailMapper: CityDetailMapper

    @Before
    fun setUp() {
        cityDetailMapper = CityDetailMapper()
    }

    @Test
    fun `Test map data`() {
        val networkModel = CityDetailNetworkModel(
            "code", "name", "currency", "countryCode", false, "timezone",
            emptyList(), false, "languageCode"
        )
        val result = cityDetailMapper.map(networkModel)

        result.code `should equal` "code"
        result.name `should equal` "name"
        result.countryCode `should equal` "countryCode"
        result.workingArea `should equal` emptyList()
        result.cityInfo?.apply {
            currency `should equal` "currency"
            isEnabled `should equal` false
            isBusy `should equal` false
            timeZone `should equal` "timezone"
            languageCode `should equal` "languageCode"
        }
    }
}
