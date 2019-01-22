package com.example.glovochallenge.glovochallenge.presentation.citysearch.di

import com.example.glovochallenge.glovochallenge.presentation.citysearch.CitySearchActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent(modules = [CitySearchModule::class])
interface CitySearchComponent : AndroidInjector<CitySearchActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<CitySearchActivity>()
}
