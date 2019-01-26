package com.example.glovochallenge.glovochallenge.domain.interactor

import com.example.glovochallenge.glovochallenge.core.Mapper
import com.example.glovochallenge.glovochallenge.data.model.CityInfoNetworkModel
import com.example.glovochallenge.glovochallenge.data.model.CountryNetworkModel
import com.example.glovochallenge.glovochallenge.data.repository.CityRepository
import com.example.glovochallenge.glovochallenge.data.repository.CountryRepository
import com.example.glovochallenge.glovochallenge.domain.model.City
import com.example.glovochallenge.glovochallenge.domain.model.Country
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CountryGroupInteractorImplTest {

    @Mock
    lateinit var cityRepository: CityRepository

    @Mock
    lateinit var countryRepository: CountryRepository

    @Mock
    lateinit var countryMapper: Mapper<CountryNetworkModel, Country>

    @Mock
    lateinit var cityInfoMapper: Mapper<CityInfoNetworkModel, City>


    private lateinit var countryGroupInteractorImpl: CountryGroupInteractorImpl

    @Before
    fun setUp() {
        countryGroupInteractorImpl =
                CountryGroupInteractorImpl(cityRepository, countryRepository, countryMapper, cityInfoMapper)
    }

    @Test
    fun `Test getCacheCountryWithCityGroup return empty hash map when no country response`() {
        whenever(countryRepository.getCountryList()).thenReturn(Single.just(emptyList()))
        countryGroupInteractorImpl.getCacheCountryWithCityGroup().test().assertResult(HashMap())
    }

    @Test
    fun `Test getCacheCountryWithCityGroup return empty hash map when no cache city`() {
        whenever(countryRepository.getCountryList()).thenReturn(Single.just(listOf(
            CountryNetworkModel("code", "name")
        )))
        whenever(cityRepository.cacheCityList).thenReturn(null)
        countryGroupInteractorImpl.getCacheCountryWithCityGroup().test().assertResult(HashMap())
    }

    @Test
    fun `Test getCacheCountryWithCityGroup return not found city that belong to country`() {
        whenever(countryRepository.getCountryList()).thenReturn(Single.just(listOf(
            CountryNetworkModel("code", "name")
        )))
        whenever(cityRepository.cacheCityList).thenReturn(listOf(
            CityInfoNetworkModel(emptyList(), "cityCode", "name", "otherCountryCode")
        ))
        countryGroupInteractorImpl.getCacheCountryWithCityGroup().test().assertResult(HashMap())
    }

    @Test
    fun `Test getCacheCountryWithCityGroup return hashmap of country with city list`() {
        whenever(countryRepository.getCountryList()).thenReturn(Single.just(listOf(
            CountryNetworkModel("code", "name")
        )))
        whenever(cityRepository.cacheCityList).thenReturn(listOf(
            CityInfoNetworkModel(emptyList(), "cityCode", "name", "code")
        ))
        val countryDomainModel = Country("code", "name")
        val cityDomainModel = City("cityCode", "name", emptyList(), "code", null)
        whenever(countryMapper.map(any())).thenReturn(countryDomainModel)
        whenever(cityInfoMapper.map(any())).thenReturn(cityDomainModel)

        val result = HashMap<Country, List<City>>()
        result[countryDomainModel] = listOf(cityDomainModel)
        countryGroupInteractorImpl.getCacheCountryWithCityGroup().test().assertResult(result)
    }
}
