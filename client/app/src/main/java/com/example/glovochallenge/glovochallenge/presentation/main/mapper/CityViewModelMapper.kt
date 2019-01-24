package com.example.glovochallenge.glovochallenge.presentation.main.mapper

import com.example.glovochallenge.glovochallenge.core.Mapper
import com.example.glovochallenge.glovochallenge.domain.model.City
import com.example.glovochallenge.glovochallenge.presentation.main.model.CityViewModel
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.PolyUtil

class CityViewModelMapper : Mapper<City, CityViewModel?> {
    override fun map(value: City): CityViewModel? =
        with(value) {
            ifNotNull(cityInfo?.currency, cityInfo?.isEnabled, cityInfo?.isBusy, cityInfo?.timeZone,
                cityInfo?.languageCode) { currency, isEnabled, isBusy, timeZone, languageCode ->
                val list = workingArea.map { PolyUtil.decode((it))}
                val latLngBuilder = LatLngBounds.builder()
                list.forEach { latLangList ->
                    latLangList.forEach { latLang ->
                        latLngBuilder.include(latLang)
                    }
                }
                CityViewModel(code, name, latLngBuilder.build(), countryCode, currency, isEnabled, isBusy, timeZone, languageCode)
            }
        }


    private fun <A, B, C, D, E, R> ifNotNull(a: A? = null, b: B? = null, c: C? = null, d: D? = null, e: E? = null,
                                     code: (A, B, C, D, E) -> R): R? {
        if (a != null && b != null && c != null && d != null && e != null) {
            return code(a, b, c, d, e)
        }
        return null
    }
}
