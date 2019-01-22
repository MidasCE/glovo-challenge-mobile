package com.example.glovochallenge.glovochallenge.domain.interactor

import com.example.glovochallenge.glovochallenge.data.repository.CityRepository
import com.example.glovochallenge.glovochallenge.data.repository.CountryRepository
import com.example.glovochallenge.glovochallenge.domain.mapper.CityDetailMapper
import com.example.glovochallenge.glovochallenge.domain.mapper.CountryMapper
import com.example.glovochallenge.glovochallenge.domain.model.City
import com.example.glovochallenge.glovochallenge.domain.model.Country
import io.reactivex.Single
import io.reactivex.rxkotlin.zipWith

class CityGroupInteractorImpl(
    private val cityRepository: CityRepository,
    private val countryRepository: CountryRepository,
    private val cityDetailMapper: CityDetailMapper,
    private val countryMapper: CountryMapper
) : CityGroupInteractor {

    override fun getCityGroup(): Single<HashMap<Country, List<City>>> =
        cityRepository.getCityList().zipWith(countryRepository.getCountryList()).map {
            val groupCity = HashMap<Country, List<City>>()
            val countryList = it.second.map { countryNetworkModel ->
                countryMapper.map(countryNetworkModel)
            }
            val cityList = it.first.map { cityNetworkModel ->
                cityDetailMapper.map(cityNetworkModel)
            }
            countryList.forEach { country ->
                val city = cityList.filter { city -> city.countryCode == country.code }
                groupCity[country] = city
            }
            groupCity
        }

}
