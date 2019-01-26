package com.example.glovochallenge.glovochallenge.domain.interactor

import com.example.glovochallenge.glovochallenge.core.Mapper
import com.example.glovochallenge.glovochallenge.data.model.CityDetailNetworkModel
import com.example.glovochallenge.glovochallenge.data.model.CityInfoNetworkModel
import com.example.glovochallenge.glovochallenge.data.repository.CityRepository
import com.example.glovochallenge.glovochallenge.data.repository.SettingsRepository
import com.example.glovochallenge.glovochallenge.domain.model.City
import io.reactivex.Single

class CityInfoInteractorImpl(
    private val cityRepository: CityRepository,
    private val settingsRepository: SettingsRepository,
    private val cityDetailMapper: Mapper<CityDetailNetworkModel, City>,
    private val cityInfoMapper: Mapper<CityInfoNetworkModel, City>
) : CityInfoInteractor {

    override fun getCachedCityList(): List<City> =
        cityRepository.cacheCityList?.map {
            cityInfoMapper.map(it)
        }.orEmpty()

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
