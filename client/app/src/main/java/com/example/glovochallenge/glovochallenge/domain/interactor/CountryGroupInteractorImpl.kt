package com.example.glovochallenge.glovochallenge.domain.interactor

import com.example.glovochallenge.glovochallenge.core.Mapper
import com.example.glovochallenge.glovochallenge.data.model.CityInfoNetworkModel
import com.example.glovochallenge.glovochallenge.data.model.CountryNetworkModel
import com.example.glovochallenge.glovochallenge.data.repository.CityRepository
import com.example.glovochallenge.glovochallenge.data.repository.CountryRepository
import com.example.glovochallenge.glovochallenge.domain.model.City
import com.example.glovochallenge.glovochallenge.domain.model.Country
import io.reactivex.Single

class CountryGroupInteractorImpl(
        private val cityRepository: CityRepository,
        private val countryRepository: CountryRepository,
        private val countryMapper: Mapper<CountryNetworkModel, Country>,
        private val cityInfoMapper: Mapper<CityInfoNetworkModel, City>
) : CountryGroupInteractor {

    override fun getCacheCountryWithCityGroup(): Single<HashMap<Country, List<City>>> {
        return countryRepository.getCountryList().map { countryList ->
            val countryGroupHashMap = HashMap<Country, List<City>>()
            cityRepository.cacheCityList?.let { cityList ->
                countryList.forEach { country ->
                    val cityFilterList = cityList.filter { city -> city.countryCode == country.code }
                    countryGroupHashMap[countryMapper.map(country)] = cityFilterList.map { cityInfoMapper.map(it) }
                }
                countryGroupHashMap
            }
        }
    }

}
