package com.example.glovochallenge.glovochallenge.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.amshove.kluent.`should equal`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SettingsRepositoryImplTest {

    @Mock
    lateinit var context: Context

    @Mock
    lateinit var sharedPreferences: SharedPreferences

    @Mock
    lateinit var editor: SharedPreferences.Editor

    private lateinit var settingsRepositoryImpl: SettingsRepositoryImpl

    @Before
    fun setUp() {
        settingsRepositoryImpl = SettingsRepositoryImpl(context)
        whenever(
            context.getSharedPreferences(
                SettingsRepositoryImpl.APP_SETTINGS_FILE, Context.MODE_PRIVATE
            )
        ).thenReturn(sharedPreferences)
        whenever(sharedPreferences.edit()).thenReturn(editor)
    }

    @Test
    fun `Test getSelectCityCode`() {
        whenever(sharedPreferences.getString(SettingsRepositoryImpl.CITY_CODE, "")).thenReturn("code")

        settingsRepositoryImpl.getSelectCityCode() `should equal` "code"
    }

    @Test
    fun `Test saveSelectCityCode`() {
        settingsRepositoryImpl.saveSelectCityCode("code")

        verify(editor).putString(SettingsRepositoryImpl.CITY_CODE, "code")
        verify(editor).commit()
    }

    @Test
    fun `Test clearSelectedCityCode`() {
        settingsRepositoryImpl.clearSelectedCityCode()

        verify(editor).clear()
        verify(editor).commit()
    }

}
