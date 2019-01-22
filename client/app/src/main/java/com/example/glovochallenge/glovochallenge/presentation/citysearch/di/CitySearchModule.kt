package com.example.glovochallenge.glovochallenge.presentation.citysearch.di

import com.example.glovochallenge.glovochallenge.presentation.citysearch.CitySearchActivity
import com.example.glovochallenge.glovochallenge.presentation.citysearch.CitySearchView
import com.example.glovochallenge.glovochallenge.presentation.citysearch.list.CitySearchAdapter
import dagger.Module
import dagger.Provides

@Module
class CitySearchModule {

    @Provides
    fun provideCitySearchView(citySearchActivity: CitySearchActivity) : CitySearchView = citySearchActivity

    @Provides
    fun provideCitySearchAdapter(): CitySearchAdapter = CitySearchAdapter()
}
