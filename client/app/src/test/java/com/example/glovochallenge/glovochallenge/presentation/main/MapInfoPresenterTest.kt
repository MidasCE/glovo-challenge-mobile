package com.example.glovochallenge.glovochallenge.presentation.main

import com.example.glovochallenge.glovochallenge.core.Mapper
import com.example.glovochallenge.glovochallenge.core.scheduler.SchedulerFactory
import com.example.glovochallenge.glovochallenge.data.model.CityInfoNetworkModel
import com.example.glovochallenge.glovochallenge.data.model.CoordinateEntity
import com.example.glovochallenge.glovochallenge.domain.interactor.CityCodeInteractor
import com.example.glovochallenge.glovochallenge.domain.interactor.CityInfoInteractor
import com.example.glovochallenge.glovochallenge.domain.interactor.LocationInteractor
import com.example.glovochallenge.glovochallenge.domain.model.City
import com.example.glovochallenge.glovochallenge.presentation.main.model.CityDetailViewModel
import com.example.glovochallenge.glovochallenge.presentation.main.model.WorkingAreaViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.PolyUtil
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
class MapInfoPresenterTest {

    @Mock
    lateinit var locationInteractor: LocationInteractor

    @Mock
    lateinit var cityInfoInteractor: CityInfoInteractor

    @Mock
    lateinit var cityCodeInteractor: CityCodeInteractor

    @Mock
    lateinit var schedulerFactory: SchedulerFactory

    @Mock
    lateinit var workingAreaViewModelMapper: Mapper<City, WorkingAreaViewModel?>

    @Mock
    lateinit var cityDetailViewModelMapper: Mapper<City, CityDetailViewModel?>

    @Mock
    lateinit var view: MapInfoView

    private lateinit var presenter: MapInfoPresenterImpl

    private lateinit var ioScheduler: TestScheduler

    private lateinit var mainScheduler: TestScheduler

    @Before
    fun setUp() {
        ioScheduler = TestScheduler()
        mainScheduler = TestScheduler()

        whenever(schedulerFactory.io()).thenReturn(ioScheduler)
        whenever(schedulerFactory.main()).thenReturn(mainScheduler)

        presenter =
                MapInfoPresenterImpl(locationInteractor, cityInfoInteractor, cityCodeInteractor, schedulerFactory,
                    workingAreaViewModelMapper, cityDetailViewModelMapper, view)
    }

    @Test
    fun `Test firstLoad when permission not granted`() {
        whenever(locationInteractor.isPermissionGranted()).thenReturn(false)

        presenter.firstLoad()

        verify(view).navigateToPermissionSettings()
    }

    @Test
    fun `Test firstLoad when cityInfoInteractor getCityList() return error `() {
        whenever(locationInteractor.isPermissionGranted()).thenReturn(true)
        whenever(cityInfoInteractor.getCityList()).thenReturn(Single.error(Exception()))
        whenever(locationInteractor.getCurrentLocation()).thenReturn(Single.error(Exception()))

        presenter.firstLoad()
        ioScheduler.triggerActions()
        mainScheduler.triggerActions()

        verify(view).showMessage("Something went wrong at our end. Please try later.")
    }

    @Test
    fun `Test firstLoad when locationInteractor getCurrentLocation() return error `() {
        whenever(locationInteractor.isPermissionGranted()).thenReturn(true)
        whenever(cityInfoInteractor.getCityList()).thenReturn(Single.just(listOf(
            City("code", "name", emptyList(), "countryCode", null)
        )))
        whenever(locationInteractor.getCurrentLocation()).thenReturn(Single.error(Exception()))

        presenter.firstLoad()
        ioScheduler.triggerActions()
        mainScheduler.triggerActions()

        verify(view).showMessage("Something went wrong at our end. Please try later.")
    }

    @Test
    fun `Test firstLoad when workingAreaViewModelMapper return null`() {
        val domainModel = City("code", "name", getLaPlataWorkArea(), "countryCode", null)

        whenever(locationInteractor.isPermissionGranted()).thenReturn(true)
        whenever(cityInfoInteractor.getCityList()).thenReturn(Single.just(listOf(domainModel)))
        whenever(locationInteractor.getCurrentLocation()).thenReturn(Single.just(CoordinateEntity(-34.920889, -57.954611)))
        whenever(workingAreaViewModelMapper.map(domainModel)).thenReturn(null)

        presenter.firstLoad()
        ioScheduler.triggerActions()
        mainScheduler.triggerActions()

        verify(view).showMessage("Not found any working area.")
    }

    @Test
    fun `Test firstLoad when location found in city area`() {
        val domainModel = City("code", "name", getLaPlataWorkArea(), "countryCode", null)
        val viewModel = map(domainModel)
        whenever(locationInteractor.isPermissionGranted()).thenReturn(true)
        whenever(cityInfoInteractor.getCityList()).thenReturn(Single.just(listOf(domainModel)))
        whenever(locationInteractor.getCurrentLocation()).thenReturn(Single.just(CoordinateEntity(-34.920889, -57.954611)))
        whenever(workingAreaViewModelMapper.map(domainModel)).thenReturn(viewModel)

        presenter.firstLoad()
        ioScheduler.triggerActions()
        mainScheduler.triggerActions()

        verify(cityCodeInteractor).saveSelectCityCode(viewModel!!.code)
        verify(view).setMapLocation(viewModel.workingBoundary)
    }

    @Test
    fun `Test firstLoad when location no working area`() {
        val domainModel = City("code", "name", emptyList(), "countryCode", null)
        val viewModel = map(domainModel)
        whenever(locationInteractor.isPermissionGranted()).thenReturn(true)
        whenever(cityInfoInteractor.getCityList()).thenReturn(Single.just(listOf(domainModel)))
        whenever(locationInteractor.getCurrentLocation()).thenReturn(Single.just(CoordinateEntity(-34.920889, -57.954611)))
        whenever(workingAreaViewModelMapper.map(domainModel)).thenReturn(viewModel)

        presenter.firstLoad()
        ioScheduler.triggerActions()
        mainScheduler.triggerActions()

        verify(view).showMessage("Not found any working area.")
    }

    @Test
    fun `Test firstLoad when location not found location in working area`() {
        val domainModel = City("code", "name", getLaPlataWorkArea(), "countryCode", null)
        val viewModel = map(domainModel)
        whenever(locationInteractor.isPermissionGranted()).thenReturn(true)
        whenever(cityInfoInteractor.getCityList()).thenReturn(Single.just(listOf(domainModel)))
        whenever(locationInteractor.getCurrentLocation()).thenReturn(Single.just(CoordinateEntity(10.0, 10.0)))
        whenever(workingAreaViewModelMapper.map(domainModel)).thenReturn(viewModel)

        presenter.firstLoad()
        ioScheduler.triggerActions()
        mainScheduler.triggerActions()

        verify(view).navigateToCitySearch()
    }

    @Test
    fun `Test loadCityDetail when receive error`() {
        whenever(cityInfoInteractor.getCityDetail()).thenReturn(Single.error(Exception()))

        presenter.loadCityDetail()
        ioScheduler.triggerActions()
        mainScheduler.triggerActions()

        verify(view).showMessage("Something went wrong at our end. Please try later.")
    }

    @Test
    fun `Test loadCityDetail when mapper return null`() {
        val domainModel = City("code", "name", emptyList(), "countryCode", null)
        whenever(cityInfoInteractor.getCityDetail()).thenReturn(Single.just(domainModel))

        presenter.loadCityDetail()
        ioScheduler.triggerActions()
        mainScheduler.triggerActions()

        verify(view, never()).updateCityDetailInformation(any())
    }

    @Test
    fun `Test loadCityDetail not first load`() {
        val domainModel = City("code", "name", emptyList(), "countryCode", null)
        val cityDetailViewModel = CityDetailViewModel("code", "name", "countryCode", "currency", false, false, "timezone", "languageCode")
        whenever(cityInfoInteractor.getCityDetail()).thenReturn(Single.just(domainModel))
        whenever(cityDetailViewModelMapper.map(domainModel)).thenReturn(
            cityDetailViewModel
        )

        presenter.loadCityDetail()
        ioScheduler.triggerActions()
        mainScheduler.triggerActions()

        verify(view).updateCityDetailInformation(cityDetailViewModel)
        verify(view, never()).setMapLocation(any())
    }

    @Test
    fun `Test loadCityDetail first load`() {
        val domainModel = City("code", "name", getLaPlataWorkArea(), "countryCode", null)
        val cityDetailViewModel = CityDetailViewModel("code", "name", "countryCode", "currency", false, false, "timezone", "languageCode")
        val workingAreaViewModel = map(domainModel)
        whenever(cityInfoInteractor.getCityDetail()).thenReturn(Single.just(domainModel))
        whenever(cityDetailViewModelMapper.map(domainModel)).thenReturn(
            cityDetailViewModel
        )
        whenever(workingAreaViewModelMapper.map(domainModel)).thenReturn(workingAreaViewModel)

        presenter.loadCityDetail(true)
        ioScheduler.triggerActions()
        mainScheduler.triggerActions()

        verify(view).updateCityDetailInformation(cityDetailViewModel)
        verify(view).setMapLocation(workingAreaViewModel!!.workingBoundary)
    }

    @Test
    fun `Test handleWorkingArea when null cached city`() {
        val domainModel = City("code", "name", getLaPlataWorkArea(), "countryCode", null)
        val workingAreaViewModel = map(domainModel)

        presenter.handleWorkingArea(false, workingAreaViewModel!!.workingBoundary)

        verify(view, never()).generateWorkingArea(any())
    }

    @Test
    fun `Test handleWorkingArea when empty cached city`() {
        whenever(cityInfoInteractor.getCachedCityList()).thenReturn(emptyList())
        val domainModel = City("code", "name", getLaPlataWorkArea(), "countryCode", null)
        val workingAreaViewModel = map(domainModel)

        presenter.handleWorkingArea(false, workingAreaViewModel!!.workingBoundary)

        verify(view, never()).generateWorkingArea(any())
    }

    @Test
    fun `Test handleWorkingArea when mapper return null`() {
        val domainModel = City("code", "name", getLaPlataWorkArea(), "countryCode", null)
        whenever(cityInfoInteractor.getCachedCityList()).thenReturn(listOf(domainModel))
        val workingAreaViewModel = map(domainModel)

        presenter.handleWorkingArea(false, workingAreaViewModel!!.workingBoundary)

        verify(view, never()).generateWorkingArea(any())
    }

    @Test
    fun `Test handleWorkingArea when latlng not contain in bound`() {
        val domainModel = City("code", "name", getLaPlataWorkArea(), "countryCode", null)
        whenever(cityInfoInteractor.getCachedCityList()).thenReturn(listOf(domainModel))
        val workingAreaViewModel = map(domainModel)
        whenever(workingAreaViewModelMapper.map(domainModel)).thenReturn(workingAreaViewModel)

        presenter.handleWorkingArea(false, LatLngBounds(LatLng(10.0, 10.0), LatLng(11.0, 11.0)))

        verify(view, never()).generateWorkingArea(any())
    }

    @Test
    fun `Test handleWorkingArea when zoom out`() {
        val domainModel = City("code", "name", getLaPlataWorkArea(), "countryCode", null)
        whenever(cityInfoInteractor.getCachedCityList()).thenReturn(listOf(domainModel))
        val workingAreaViewModel = map(domainModel)
        whenever(workingAreaViewModelMapper.map(domainModel)).thenReturn(workingAreaViewModel)
        whenever(cityInfoInteractor.getCityDetail()).thenReturn(Single.just(domainModel))

        presenter.handleWorkingArea(false, workingAreaViewModel!!.workingBoundary)

        verify(view).generateWorkingArea(listOf(workingAreaViewModel))

        verify(cityCodeInteractor).saveSelectCityCode(workingAreaViewModel.code)
        verify(cityInfoInteractor).getCityDetail()
    }

    @Test
    fun `Test handleWorkingArea when zoom in`() {
        val domainModel = City("code", "name", getLaPlataWorkArea(), "countryCode", null)
        whenever(cityInfoInteractor.getCachedCityList()).thenReturn(listOf(domainModel))
        val workingAreaViewModel = map(domainModel)
        whenever(workingAreaViewModelMapper.map(domainModel)).thenReturn(workingAreaViewModel)
        whenever(cityInfoInteractor.getCityDetail()).thenReturn(Single.just(domainModel))

        presenter.handleWorkingArea(false, workingAreaViewModel!!.workingBoundary)

        verify(view).generateMarkerWorkingArea(listOf(workingAreaViewModel))

        verify(cityCodeInteractor).saveSelectCityCode(workingAreaViewModel.code)
        verify(cityInfoInteractor).getCityDetail()
    }

    @Test
    fun `Test onMarkerClick when null cached city`() {
        presenter.onMarkerClick("name")

        verify(view, never()).zoomMapLocation(any())
    }

    @Test
    fun `Test onMarkerClick when empty cached city`() {
        whenever(cityInfoInteractor.getCachedCityList()).thenReturn(emptyList())

        presenter.onMarkerClick("name")

        verify(view, never()).zoomMapLocation(any())
    }

    @Test
    fun `Test onMarkerClick when not found city name`() {
        val domainModel = City("code", "name", getLaPlataWorkArea(), "countryCode", null)
        whenever(cityInfoInteractor.getCachedCityList()).thenReturn(listOf(
            domainModel
        ))

        presenter.onMarkerClick("otherName")

        verify(view, never()).zoomMapLocation(any())
    }

    @Test
    fun `Test onMarkerClick when mapper return null`() {
        val domainModel = City("code", "name", getLaPlataWorkArea(), "countryCode", null)
        whenever(cityInfoInteractor.getCachedCityList()).thenReturn(listOf(
            domainModel
        ))

        presenter.onMarkerClick("name")

        verify(view, never()).zoomMapLocation(any())
    }

    @Test
    fun `Test onMarkerClick when mapper return non null`() {
        val domainModel = City("code", "name", getLaPlataWorkArea(), "countryCode", null)
        val workingAreaViewModel = map(domainModel)
        whenever(cityInfoInteractor.getCityDetail()).thenReturn(Single.just(domainModel))
        whenever(cityInfoInteractor.getCachedCityList()).thenReturn(listOf(
            domainModel
        ))
        whenever(workingAreaViewModelMapper.map(domainModel)).thenReturn(workingAreaViewModel)

        presenter.onMarkerClick("name")

        verify(cityCodeInteractor).saveSelectCityCode(workingAreaViewModel!!.code)
        verify(cityInfoInteractor).getCityDetail()
        verify(view).zoomMapLocation(workingAreaViewModel.workingBoundary.center)
    }

    @Test
    fun `Test onReceivedLocationPermissionResponse true`() {
        presenter.onReceivedLocationPermissionResponse(true)

        verify(locationInteractor).isPermissionGranted()
    }

    @Test
    fun `Test onReceivedLocationPermissionResponse false`() {
        presenter.onReceivedLocationPermissionResponse(false)

        verify(view).navigateToCitySearch()
    }

    private fun map(value: City): WorkingAreaViewModel? =
        with(value) {
            val list = workingArea.map { PolyUtil.decode((it)) }
            val latLngBuilder = LatLngBounds.builder()
            list.takeIf { it.isNotEmpty() }?.forEach { latLangList ->
                latLangList.forEach { latLang ->
                    latLngBuilder.include(latLang)
                }
            }?.let {
                WorkingAreaViewModel(code, name, list, latLngBuilder.build())
            }
        }

    private fun getLaPlataWorkArea(): List<String> = listOf("",
        "~d_tE~gs`Jn}CmuExuB|}B`nAtxAad@fu@zx@p_AigBhrCevBj}C_xBojCuyBmpC~Zw`@kE{S")

}
