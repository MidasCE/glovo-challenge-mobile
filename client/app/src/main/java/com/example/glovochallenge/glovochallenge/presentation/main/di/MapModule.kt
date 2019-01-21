package com.example.glovochallenge.glovochallenge.presentation.main.di

import com.example.glovochallenge.glovochallenge.presentation.main.MapActivity
import com.example.glovochallenge.glovochallenge.presentation.main.MapView
import dagger.Module
import dagger.Provides

@Module
class MapModule{

    @Provides
    fun provideWeatherView(mapActivity: MapActivity) : MapView = mapActivity

}
