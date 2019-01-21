package com.example.glovochallenge.glovochallenge.data.repository

import android.content.Context

class SettingsRepositoryImpl(private val context: Context) : SettingsRepository {

    companion object {
        const val APP_SETTINGS_FILE = "app_settings"
        const val CITY_CODE = "city_code"
    }

    override fun saveSelectCityCode(cityCode : String) {
        val sharedPref = context.getSharedPreferences(
            APP_SETTINGS_FILE, Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putString(CITY_CODE, cityCode)
            apply()
        }
    }

    override fun getSelectCityCode() : String? {
        val sharedPref = context.getSharedPreferences(
            APP_SETTINGS_FILE, Context.MODE_PRIVATE)
        return sharedPref.getString(CITY_CODE, "")
    }
}
