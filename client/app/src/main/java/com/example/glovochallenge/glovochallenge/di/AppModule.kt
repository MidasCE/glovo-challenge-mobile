package com.example.glovochallenge.glovochallenge.di

import android.app.Application
import android.content.Context
import com.example.glovochallenge.glovochallenge.presentation.main.di.MapComponent
import dagger.Module
import dagger.Provides

@Module(subcomponents = [MapComponent::class])
class AppModule {

    @Provides
    fun provideContext(application: Application): Context = application

}