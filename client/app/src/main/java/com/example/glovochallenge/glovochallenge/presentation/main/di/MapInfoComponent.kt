package com.example.glovochallenge.glovochallenge.presentation.main.di

import com.example.glovochallenge.glovochallenge.presentation.main.MapInfoActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent(modules = [MapInfoModule::class])
interface MapInfoComponent : AndroidInjector<MapInfoActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MapInfoActivity>()
}
