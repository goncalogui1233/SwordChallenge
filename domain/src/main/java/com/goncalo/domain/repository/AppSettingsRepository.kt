package com.goncalo.domain.repository

interface AppSettingsRepository {

    suspend fun setAppStartupFlag(isAppFirstRun: Boolean)

}