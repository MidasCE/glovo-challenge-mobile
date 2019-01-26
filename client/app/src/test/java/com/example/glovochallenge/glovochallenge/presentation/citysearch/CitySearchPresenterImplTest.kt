package com.example.glovochallenge.glovochallenge.presentation.citysearch

import com.example.glovochallenge.glovochallenge.core.scheduler.SchedulerFactory
import com.example.glovochallenge.glovochallenge.domain.interactor.CityCodeInteractor
import com.example.glovochallenge.glovochallenge.domain.interactor.CountryGroupInteractor
import com.example.glovochallenge.glovochallenge.domain.model.City
import com.example.glovochallenge.glovochallenge.domain.model.Country
import com.example.glovochallenge.glovochallenge.presentation.citysearch.model.CitySearchItem
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CitySearchPresenterImplTest {

    @Mock
    lateinit var countryGroupInteractor: CountryGroupInteractor

    @Mock
    lateinit var cityCodeInteractor: CityCodeInteractor

    @Mock
    lateinit var schedulerFactory: SchedulerFactory

    @Mock
    lateinit var citySearchView: CitySearchView

    private lateinit var citySearchPresenterImpl: CitySearchPresenterImpl

    private lateinit var ioScheduler: TestScheduler

    private lateinit var mainScheduler: TestScheduler

    @Before
    fun setUp() {
        ioScheduler = TestScheduler()
        mainScheduler = TestScheduler()

        whenever(schedulerFactory.io()).thenReturn(ioScheduler)
        whenever(schedulerFactory.main()).thenReturn(mainScheduler)

        citySearchPresenterImpl =
                CitySearchPresenterImpl(countryGroupInteractor, cityCodeInteractor, schedulerFactory, citySearchView)
    }

    @Test
    fun `Test loadCityGroup`() {
        val result = HashMap<Country, List<City>>()
        val countryDomainModel = Country("code", "countryName")
        val cityDomainModel = City("cityCode", "name", emptyList(), "code", null)
        result[countryDomainModel] = listOf(cityDomainModel)
        whenever(countryGroupInteractor.getCacheCountryWithCityGroup()).thenReturn(Single.just(result))

        citySearchPresenterImpl.loadCityGroup()
        ioScheduler.triggerActions()
        mainScheduler.triggerActions()

        val itemList = listOf(
            CitySearchItem.CountryItem("countryName"),
            CitySearchItem.CityItem("name", "cityCode")
        )
        verify(citySearchView).showCityGroup(itemList)
    }

    @Test
    fun `Test loadCityGroup get error`() {
        whenever(countryGroupInteractor.getCacheCountryWithCityGroup()).thenReturn(Single.error(Exception()))

        citySearchPresenterImpl.loadCityGroup()
        ioScheduler.triggerActions()
        mainScheduler.triggerActions()

        verify(citySearchView, never()).showCityGroup(any())
    }

    @Test
    fun `Test saveSelectCityCode`() {
        citySearchPresenterImpl.saveSelectCityCode("code")

        verify(cityCodeInteractor).saveSelectCityCode("code")
        verify(citySearchView).navigateBackTomapView()
    }

    @Test
    fun `Test onBackPressed()`() {
        citySearchPresenterImpl.onBackPressed()

        verify(cityCodeInteractor).clearSelectedCityCode()
        verify(citySearchView).navigateBackTomapView()
    }
}
