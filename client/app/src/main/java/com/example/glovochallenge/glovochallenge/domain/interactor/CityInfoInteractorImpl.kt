package com.example.glovochallenge.glovochallenge.domain.interactor

import com.example.glovochallenge.glovochallenge.data.repository.CityRepository
import com.example.glovochallenge.glovochallenge.data.repository.CountryRepository
import com.example.glovochallenge.glovochallenge.data.repository.SettingsRepository
import com.example.glovochallenge.glovochallenge.domain.mapper.CityDetailMapper
import com.example.glovochallenge.glovochallenge.domain.mapper.CityInfoMapper
import com.example.glovochallenge.glovochallenge.domain.mapper.CountryMapper
import com.example.glovochallenge.glovochallenge.domain.model.City
import com.example.glovochallenge.glovochallenge.domain.model.Country
import io.reactivex.Single
import io.reactivex.rxkotlin.zipWith

class CityInfoInteractorImpl(
    private val cityRepository: CityRepository,
    private val settingsRepository: SettingsRepository,
    private val countryRepository: CountryRepository,
    private val cityDetailMapper: CityDetailMapper,
    private val cityInfoMapper: CityInfoMapper,
    private val countryMapper: CountryMapper
) : CityInfoInteractor {

    override fun getCityList(): Single<List<City>> =
        cityRepository.getCityList().map {
            it.map { cityInfo ->
                cityInfoMapper.map(cityInfo)
            }
        }

    override fun getCityDetail(): Single<City> =
        cityRepository.getCityDetail(settingsRepository.getSelectCityCode() ?: "").map {
            cityDetailMapper.map(it)
        }

}
