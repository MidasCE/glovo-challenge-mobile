package com.example.glovochallenge.glovochallenge.presentation.main.di

import com.example.glovochallenge.glovochallenge.presentation.main.MapActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent(modules = [MapModule::class])
interface MapComponent : AndroidInjector<MapActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MapActivity>()
}
