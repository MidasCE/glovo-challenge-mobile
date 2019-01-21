package com.example.glovochallenge.glovochallenge.di

import android.app.Application
import com.example.glovochallenge.glovochallenge.AndroidApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AppModule::class,
    ActivityBuilder::class])
interface AppComponent {

    @Component.Builder
    interface Builder : ComponentBuilder<AppComponent> {
        /**
         * will allow clients of this builder to pass their own instances,
         * and those instances can be injected within the component
         */
        @BindsInstance
        fun application(application: Application): Builder
    }

    fun inject(app: AndroidApplication)
}

/**
 * Helper interface to help builder class create a compoment
 */
interface ComponentBuilder<out C> {
    fun build(): C
}