package com.example.glovochallenge.glovochallenge.domain.interactor

import com.example.glovochallenge.glovochallenge.data.repository.SettingsRepository
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CityCodeInteractorImplTest {

    @Mock
    lateinit var settingsRepository: SettingsRepository

    private lateinit var cityCodeInteractorImpl: CityCodeInteractorImpl

    @Before
    fun setUp() {
        cityCodeInteractorImpl = CityCodeInteractorImpl(settingsRepository)
    }

    @Test
    fun `Test saveSelectCityCode`() {
        cityCodeInteractorImpl.saveSelectCityCode("code")

        verify(settingsRepository).saveSelectCityCode("code")
    }

    @Test
    fun `Test clearSelectedCityCode`() {
        cityCodeInteractorImpl.clearSelectedCityCode()

        verify(settingsRepository).clearSelectedCityCode()
    }
}
