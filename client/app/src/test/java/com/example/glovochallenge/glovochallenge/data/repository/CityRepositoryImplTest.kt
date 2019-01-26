package com.example.glovochallenge.glovochallenge.data.repository

import com.example.glovochallenge.glovochallenge.data.api.NetworkAPI
import com.example.glovochallenge.glovochallenge.data.model.CityDetailNetworkModel
import com.example.glovochallenge.glovochallenge.data.model.CityInfoNetworkModel
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.amshove.kluent.`should equal`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CityRepositoryImplTest {

    @Mock
    lateinit var networkAPI: NetworkAPI

    private lateinit var cityRepositoryImpl: CityRepositoryImpl

    @Before
    fun setUp() {
        cityRepositoryImpl = CityRepositoryImpl(networkAPI)
    }

    @Test
    fun `Test call api getCityDetail response`() {
        val response = Single.just(
            CityDetailNetworkModel(
                "code", "name", "currency", "countryCode", false, "timezone",
                emptyList(), false, "languageCode"
            )
        )
        whenever(networkAPI.getCityDetail(any())).thenReturn(response)

        cityRepositoryImpl.getCityDetail("code").`should equal`(response)
    }

    @Test
    fun `Test call api getCityList response`() {
        val list = listOf(
            CityInfoNetworkModel(
                emptyList(), "code", "name", "countryCode"
            )
        )
        val response = Single.just(list)
        whenever(networkAPI.getCityList()).thenReturn(response)

        cityRepositoryImpl.getCityList().test().assertResult(list)
        cityRepositoryImpl.cacheCityList.`should equal`(list)
    }
}
