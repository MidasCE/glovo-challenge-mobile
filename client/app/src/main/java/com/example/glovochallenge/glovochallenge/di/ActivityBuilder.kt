package com.example.glovochallenge.glovochallenge.di

import android.app.Activity
import com.example.glovochallenge.glovochallenge.presentation.citysearch.CitySearchActivity
import com.example.glovochallenge.glovochallenge.presentation.citysearch.di.CitySearchComponent
import com.example.glovochallenge.glovochallenge.presentation.main.MapInfoActivity
import com.example.glovochallenge.glovochallenge.presentation.main.di.MapInfoComponent
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ActivityBuilder {

    @Binds
    @IntoMap
    @ActivityKey(MapInfoActivity::class)
    abstract fun bindMapActivity(builder: MapInfoComponent.Builder): AndroidInjector.Factory<out Activity>

    @Binds
    @IntoMap
    @ActivityKey(CitySearchActivity::class)
    abstract fun bindCitySearchActivity(builder: CitySearchComponent.Builder): AndroidInjector.Factory<out Activity>

}
