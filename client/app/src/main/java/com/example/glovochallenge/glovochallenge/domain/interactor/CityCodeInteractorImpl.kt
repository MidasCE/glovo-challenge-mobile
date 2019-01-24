package com.example.glovochallenge.glovochallenge.domain.interactor

import com.example.glovochallenge.glovochallenge.data.repository.SettingsRepository

class CityCodeInteractorImpl(private val settingsRepository: SettingsRepository) : CityCodeInteractor {

    override fun saveSelectCityCode(cityCode: String) {
        settingsRepository.saveSelectCityCode(cityCode)
    }
}
