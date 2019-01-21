package com.example.glovochallenge.glovochallenge.presentation.main.di

import com.example.glovochallenge.glovochallenge.core.Mapper
import com.example.glovochallenge.glovochallenge.data.model.CityDetailNetworkModel
import com.example.glovochallenge.glovochallenge.data.model.CityInfoNetworkModel
import com.example.glovochallenge.glovochallenge.data.model.CountryNetworkModel
import com.example.glovochallenge.glovochallenge.domain.mapper.CityDetailMapper
import com.example.glovochallenge.glovochallenge.domain.mapper.CityInfoMapper
import com.example.glovochallenge.glovochallenge.domain.mapper.CountryMapper
import com.example.glovochallenge.glovochallenge.domain.model.City
import com.example.glovochallenge.glovochallenge.domain.model.Country
import com.example.glovochallenge.glovochallenge.presentation.main.MapActivity
import com.example.glovochallenge.glovochallenge.presentation.main.MapView
import dagger.Module
import dagger.Provides

@Module
class MapModule{

    @Provides
    fun provideMapView(mapActivity: MapActivity) : MapView = mapActivity

    @Provides
    fun provideCountryMapper(): Mapper<CountryNetworkModel, Country> = CountryMapper()

    @Provides
    fun provideCityDetailMapper(): Mapper<CityDetailNetworkModel, City> = CityDetailMapper()

    @Provides
    fun provideCityInfoMapper(): Mapper<CityInfoNetworkModel, City> = CityInfoMapper()
}
