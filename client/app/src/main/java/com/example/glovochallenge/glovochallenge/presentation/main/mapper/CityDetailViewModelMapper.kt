package com.example.glovochallenge.glovochallenge.presentation.main.mapper

import com.example.glovochallenge.glovochallenge.core.Mapper
import com.example.glovochallenge.glovochallenge.domain.model.City
import com.example.glovochallenge.glovochallenge.presentation.main.model.CityDetailViewModel

class CityDetailViewModelMapper : Mapper<City, CityDetailViewModel?> {
    override fun map(value: City): CityDetailViewModel? =
        with(value) {
            ifNotNull(
                cityInfo?.currency, cityInfo?.isEnabled, cityInfo?.isBusy, cityInfo?.timeZone,
                cityInfo?.languageCode
            ) { currency, isEnabled, isBusy, timeZone, languageCode ->
                CityDetailViewModel(code, name, countryCode, currency, isEnabled, isBusy, timeZone, languageCode)
            }
        }

    private fun <A, B, C, D, E, R> ifNotNull(
        a: A? = null, b: B? = null, c: C? = null, d: D? = null, e: E? = null,
        code: (A, B, C, D, E) -> R
    ): R? {
        if (a != null && b != null && c != null && d != null && e != null) {
            return code(a, b, c, d, e)
        }
        return null
    }
}