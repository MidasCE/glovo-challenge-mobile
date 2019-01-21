package com.example.glovochallenge.glovochallenge.di

import android.content.Context
import com.example.glovochallenge.glovochallenge.data.provider.AppLocationProvider
import com.example.glovochallenge.glovochallenge.data.provider.AppLocationProviderImpl
import com.example.glovochallenge.glovochallenge.data.repository.SettingsRepository
import com.example.glovochallenge.glovochallenge.data.repository.SettingsRepositoryImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideLocationProvider(context: Context): AppLocationProvider {
        return AppLocationProviderImpl(context)
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl("http://127.0.0.1:3000/api/")
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
        .create()

    @Provides
    @Singleton
    fun provideSettingsRepository(context: Context): SettingsRepository =
        SettingsRepositoryImpl(context)
}
