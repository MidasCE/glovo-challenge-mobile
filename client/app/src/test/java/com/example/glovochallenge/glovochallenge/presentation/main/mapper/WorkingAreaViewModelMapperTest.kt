package com.example.glovochallenge.glovochallenge.presentation.main.mapper

import com.example.glovochallenge.glovochallenge.domain.model.City
import com.example.glovochallenge.glovochallenge.presentation.main.model.WorkingAreaViewModel
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.PolyUtil
import org.amshove.kluent.`should equal`
import org.junit.Before
import org.junit.Test

class WorkingAreaViewModelMapperTest {
    private lateinit var workingAreaViewModelMapper: WorkingAreaViewModelMapper

    @Before
    fun setUp() {
        workingAreaViewModelMapper = WorkingAreaViewModelMapper()
    }

    @Test
    fun `Test map`() {
        val city = City("code", "name", getLaPlataWorkArea(), "countryCode", null)

        workingAreaViewModelMapper.map(city) `should equal` map(city)
    }

    private fun map(value: City): WorkingAreaViewModel? =
        with(value) {
            val list = workingArea.map { PolyUtil.decode((it)) }
            val latLngBuilder = LatLngBounds.builder()
            list.takeIf { it.isNotEmpty() }?.forEach { latLangList ->
                latLangList.forEach { latLang ->
                    latLngBuilder.include(latLang)
                }
            }?.let {
                WorkingAreaViewModel(code, name, list, latLngBuilder.build())
            }
        }

    private fun getLaPlataWorkArea(): List<String> = listOf(
        "",
        "~d_tE~gs`Jn}CmuExuB|}B`nAtxAad@fu@zx@p_AigBhrCevBj}C_xBojCuyBmpC~Zw`@kE{S"
    )
}
