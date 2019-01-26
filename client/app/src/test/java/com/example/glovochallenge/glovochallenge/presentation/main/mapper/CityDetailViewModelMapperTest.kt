package com.example.glovochallenge.glovochallenge.presentation.main.mapper

import com.example.glovochallenge.glovochallenge.domain.model.City
import com.example.glovochallenge.glovochallenge.domain.model.CityInfo
import com.example.glovochallenge.glovochallenge.presentation.main.model.CityDetailViewModel
import org.amshove.kluent.`should equal`
import org.junit.Before
import org.junit.Test

class CityDetailViewModelMapperTest {

    private lateinit var cityDetailViewModelMapper: CityDetailViewModelMapper

    @Before
    fun setUp() {
        cityDetailViewModelMapper = CityDetailViewModelMapper()
    }

    @Test
    fun `Test map with null city info entity`() {
        val city = City("code", "name", emptyList(), "countryCode", null)
        cityDetailViewModelMapper.map(city) `should equal` null
    }

    @Test
    fun `Test map with non null city info entity`() {
        val city = City("code", "name", emptyList(), "countryCode",
            CityInfo(
                "currency", false, false, "timezone", "languageCode"
            ))
        cityDetailViewModelMapper.map(city) `should equal` CityDetailViewModel(
            "code", "name", "countryCode", "currency", false, false, "timezone", "languageCode"
        )
    }
}
