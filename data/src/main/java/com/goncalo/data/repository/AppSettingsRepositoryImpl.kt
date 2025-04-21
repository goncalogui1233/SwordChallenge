package com.goncalo.data.repository

import com.goncalo.data.datastore.CatDataStore
import com.goncalo.domain.repository.AppSettingsRepository
import javax.inject.Inject

class AppSettingsRepositoryImpl @Inject constructor(
    private val dataStore: CatDataStore
): AppSettingsRepository {
    override suspend fun setAppStartupFlag(isAppFirstRun: Boolean) {
        dataStore.saveAppStartupFlag(isAppFirstRun)
    }

}