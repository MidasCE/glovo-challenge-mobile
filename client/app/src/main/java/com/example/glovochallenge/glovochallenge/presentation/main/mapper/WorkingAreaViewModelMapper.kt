package com.example.glovochallenge.glovochallenge.presentation.main.mapper

import com.example.glovochallenge.glovochallenge.core.Mapper
import com.example.glovochallenge.glovochallenge.domain.model.City
import com.example.glovochallenge.glovochallenge.presentation.main.model.WorkingAreaViewModel
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.PolyUtil

class WorkingAreaViewModelMapper : Mapper<City, WorkingAreaViewModel?> {
    override fun map(value: City): WorkingAreaViewModel? =
        with(value) {
            val list = workingArea.map { PolyUtil.decode((it)) }
            val latLngBuilder = LatLngBounds.builder()
            list.forEach { latLangList ->
                latLangList.forEach { latLang ->
                    latLngBuilder.include(latLang)
                }
            }
            WorkingAreaViewModel(code, name, list, latLngBuilder.build())
        }
}
