package com.example.glovochallenge.glovochallenge.di

import com.example.glovochallenge.glovochallenge.core.Mapper
import com.example.glovochallenge.glovochallenge.data.model.CityDetailNetworkModel
import com.example.glovochallenge.glovochallenge.data.model.CityInfoNetworkModel
import com.example.glovochallenge.glovochallenge.data.model.CountryNetworkModel
import com.example.glovochallenge.glovochallenge.data.provider.AppLocationProvider
import com.example.glovochallenge.glovochallenge.data.repository.CityRepository
import com.example.glovochallenge.glovochallenge.data.repository.CountryRepository
import com.example.glovochallenge.glovochallenge.data.repository.SettingsRepository
import com.example.glovochallenge.glovochallenge.domain.interactor.*
import com.example.glovochallenge.glovochallenge.domain.mapper.CityDetailMapper
import com.example.glovochallenge.glovochallenge.domain.mapper.CityInfoMapper
import com.example.glovochallenge.glovochallenge.domain.mapper.CountryMapper
import com.example.glovochallenge.glovochallenge.domain.model.City
import com.example.glovochallenge.glovochallenge.domain.model.Country
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Provides
    fun provideCountryMapper(): Mapper<CountryNetworkModel, Country> =
            CountryMapper()

    @Provides
    fun provideCityInfoMapper(): Mapper<CityInfoNetworkModel, City> =
            CityInfoMapper()

    @Provides
    fun provideCityDetailMapper(): Mapper<CityDetailNetworkModel, City> =
            CityDetailMapper()

    @Provides
    @Singleton
    fun provideCityCodeInteractor(settingsRepository: SettingsRepository): CityCodeInteractor {
        return CityCodeInteractorImpl(settingsRepository)
    }

    @Provides
    @Singleton
    fun provideCityInfoInteractor(cityRepository: CityRepository,
                                  settingsRepository: SettingsRepository,
                                  cityDetailMapper: Mapper<CityDetailNetworkModel, City>,
                                  cityInfoMapper: Mapper<CityInfoNetworkModel, City>):
            CityInfoInteractor {
        return CityInfoInteractorImpl(cityRepository,
                settingsRepository,
                cityDetailMapper,
                cityInfoMapper)
    }

    @Provides
    @Singleton
    fun provideCountryGroupInteractor(cityRepository: CityRepository,
                                  countryRepository: CountryRepository,
                                  countryMapper: Mapper<CountryNetworkModel, Country>,
                                  cityInfoMapper: Mapper<CityInfoNetworkModel, City>):
            CountryGroupInteractor {
        return CountryGroupInteractorImpl(cityRepository,
                countryRepository,
                countryMapper,
                cityInfoMapper)
    }

    @Provides
    @Singleton
    fun provideLocationInteractor(locationProvider: AppLocationProvider): LocationInteractor =
        LocationInteratorImpl(locationProvider)

}
