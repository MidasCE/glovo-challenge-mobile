package com.example.glovochallenge.glovochallenge.data.repository

import com.example.glovochallenge.glovochallenge.data.api.NetworkAPI
import com.example.glovochallenge.glovochallenge.data.model.CountryNetworkModel
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.amshove.kluent.`should equal`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CountryRepositoryImplTest {

    @Mock
    lateinit var networkAPI: NetworkAPI

    private lateinit var countryRepositoryImpl : CountryRepositoryImpl

    @Before
    fun setUp() {
        countryRepositoryImpl = CountryRepositoryImpl(networkAPI)
    }

    @Test
    fun `Test call api getCountryList response`() {
        val list = listOf(
            CountryNetworkModel(
                "countryCode", "name"
            )
        )
        val response = Single.just(list)
        whenever(networkAPI.getCountryList()).thenReturn(response)

        countryRepositoryImpl.getCountryList().test().assertResult(list)
        countryRepositoryImpl.cacheCountryList.`should equal`(list)
    }
}
