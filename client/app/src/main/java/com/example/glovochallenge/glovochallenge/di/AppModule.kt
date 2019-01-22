package com.example.glovochallenge.glovochallenge.di

import android.app.Application
import android.content.Context
import com.example.glovochallenge.glovochallenge.core.scheduler.SchedulerFactory
import com.example.glovochallenge.glovochallenge.core.scheduler.SchedulerFactoryImpl
import com.example.glovochallenge.glovochallenge.presentation.citysearch.di.CitySearchComponent
import com.example.glovochallenge.glovochallenge.presentation.main.di.MapInfoComponent
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton

@Module(subcomponents = [MapInfoComponent::class, CitySearchComponent::class])
class AppModule {

    @Provides
    fun provideContext(application: Application): Context = application


    @Provides
    @Singleton
    @Named("main")
    fun provideMainScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Provides
    @Singleton
    @Named("io")
    fun provideIoScheduler(): Scheduler = Schedulers.io()

    @Provides
    @Singleton
    fun provideSchedulerFactory(
        @Named("io") ioScheduler: Scheduler,
        @Named("main") mainScheduler: Scheduler
    ): SchedulerFactory = SchedulerFactoryImpl(ioScheduler, mainScheduler)
}
