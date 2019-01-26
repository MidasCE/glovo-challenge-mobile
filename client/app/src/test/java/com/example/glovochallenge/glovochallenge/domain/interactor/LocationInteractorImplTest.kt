package com.example.glovochallenge.glovochallenge.domain.interactor

import com.example.glovochallenge.glovochallenge.data.model.CoordinateEntity
import com.example.glovochallenge.glovochallenge.data.provider.AppLocationProvider
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.amshove.kluent.`should equal`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LocationInteractorImplTest {

    @Mock
    lateinit var appLocationProvider: AppLocationProvider

    private lateinit var locationInteractorImpl: LocationInteractorImpl

    @Before
    fun setUp() {
        locationInteractorImpl = LocationInteractorImpl(appLocationProvider)
    }

    @Test
    fun `Test isPermissionGranted`() {
        whenever(appLocationProvider.isPermissionGranted()).thenReturn(true)

        locationInteractorImpl.isPermissionGranted() `should equal` (true)
    }

    @Test
    fun `Test getCurrentLocation`() {
        val coordinateEntity = CoordinateEntity(0.0, 0.0)
        whenever(appLocationProvider.getCurrentLocation()).thenReturn(Single.just(coordinateEntity))

        locationInteractorImpl.getCurrentLocation().test().assertResult(coordinateEntity)
    }
}
