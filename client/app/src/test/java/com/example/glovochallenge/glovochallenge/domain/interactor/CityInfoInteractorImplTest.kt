package com.example.glovochallenge.glovochallenge.domain.interactor

import com.example.glovochallenge.glovochallenge.core.Mapper
import com.example.glovochallenge.glovochallenge.data.model.CityDetailNetworkModel
import com.example.glovochallenge.glovochallenge.data.model.CityInfoNetworkModel
import com.example.glovochallenge.glovochallenge.data.repository.CityRepository
import com.example.glovochallenge.glovochallenge.data.repository.SettingsRepository
import com.example.glovochallenge.glovochallenge.domain.model.City
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.amshove.kluent.`should equal`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.lang.IllegalArgumentException

@RunWith(MockitoJUnitRunner::class)
class CityInfoInteractorImplTest {

    @Mock
    lateinit var cityRepository: CityRepository

    @Mock
    lateinit var settingsRepository: SettingsRepository

    @Mock
    lateinit var cityDetailMapper: Mapper<CityDetailNetworkModel, City>

    @Mock
    lateinit var cityInfoMapper: Mapper<CityInfoNetworkModel, City>

    private lateinit var cityInfoInteractorImpl: CityInfoInteractorImpl

    @Before
    fun setUp() {
        cityInfoInteractorImpl =
                CityInfoInteractorImpl(cityRepository, settingsRepository, cityDetailMapper, cityInfoMapper)
    }

    @Test
    fun `Test getCachedCityList`() {
        val netWorkModel = CityInfoNetworkModel(
            emptyList(), "code", "name", "countryCode"
        )
        val domainModel = City("code", "name", emptyList(), "countryCode", null)
        whenever(cityRepository.cacheCityList).thenReturn(listOf(netWorkModel))
        whenever(cityInfoMapper.map(netWorkModel)).thenReturn(domainModel)

        cityInfoInteractorImpl.getCachedCityList() `should equal` listOf(domainModel)
    }

    @Test
    fun `Test getCityList`() {
        val netWorkModel = CityInfoNetworkModel(
            emptyList(), "code", "name", "countryCode"
        )
        val domainModel = City("code", "name", emptyList(), "countryCode", null)
        whenever(cityRepository.getCityList()).thenReturn(Single.just(listOf(netWorkModel)))
        whenever(cityInfoMapper.map(netWorkModel)).thenReturn(domainModel)

        cityInfoInteractorImpl.getCityList().test().assertResult(listOf(domainModel))
    }

    @Test
    fun `Test getCityDetail`() {
        val netWorkModel =  CityDetailNetworkModel(
            "code", "name", "currency", "countryCode", false, "timezone",
            emptyList(), false, "languageCode"
        )
        val domainModel = City("code", "name", emptyList(), "countryCode", null)
        whenever(settingsRepository.getSelectCityCode()).thenReturn("code")
        whenever(cityRepository.getCityDetail("code")).thenReturn(Single.just(netWorkModel))
        whenever(cityDetailMapper.map(netWorkModel)).thenReturn(domainModel)

        cityInfoInteractorImpl.getCityDetail().test().assertResult(domainModel)
    }

    @Test
    fun `Test getCityDetail return error`() {
        whenever(settingsRepository.getSelectCityCode()).thenReturn("")

        cityInfoInteractorImpl.getCityDetail().test()
            .assertFailure(IllegalArgumentException::class.java)
    }
}
