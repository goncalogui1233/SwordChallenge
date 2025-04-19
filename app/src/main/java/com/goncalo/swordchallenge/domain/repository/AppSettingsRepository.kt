package com.goncalo.swordchallenge.domain.repository

interface AppSettingsRepository {

    suspend fun setAppStartupFlag(isAppFirstRun: Boolean)

}