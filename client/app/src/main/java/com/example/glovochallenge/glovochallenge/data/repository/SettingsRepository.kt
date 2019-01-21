package com.example.glovochallenge.glovochallenge.data.repository

interface SettingsRepository {
    fun saveSelectCityCode(cityCode : String)

    fun getSelectCityCode() : String?
}
