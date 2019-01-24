package com.example.glovochallenge.glovochallenge.di

import com.example.glovochallenge.glovochallenge.data.provider.AppLocationProvider
import com.example.glovochallenge.glovochallenge.data.repository.SettingsRepository
import com.example.glovochallenge.glovochallenge.domain.interactor.CityCodeInteractor
import com.example.glovochallenge.glovochallenge.domain.interactor.CityCodeInteractorImpl
import com.example.glovochallenge.glovochallenge.domain.interactor.LocationInteractor
import com.example.glovochallenge.glovochallenge.domain.interactor.LocationInteratorImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Provides
    @Singleton
    fun provideCityCodeInteractor(settingsRepository: SettingsRepository): CityCodeInteractor {
        return CityCodeInteractorImpl(settingsRepository)
    }

    @Provides
    @Singleton
    fun provideLocationPermissionInteractor(locationProvider: AppLocationProvider): LocationInteractor =
        LocationInteratorImpl(locationProvider)

}
