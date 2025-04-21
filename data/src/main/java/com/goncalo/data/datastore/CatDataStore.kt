package com.goncalo.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore("cat_preferences")

class CatDataStore(
    private val context: Context
) {

    companion object {
        private val CAT_LIST_LAST_PAGE = stringPreferencesKey("catListLastPage")
        private val APP_LAUNCH = stringPreferencesKey("appLaunch")

    }

    suspend fun saveCatListLastPage(page: String) {
        context.dataStore.edit {
            it[CAT_LIST_LAST_PAGE] = page
        }
    }

    suspend fun getCatListLastPage(): String? {
        return context.dataStore.data.first()[CAT_LIST_LAST_PAGE]
    }

    suspend fun saveAppStartupFlag(isAppStartup: Boolean) {
        context.dataStore.edit {
            it[APP_LAUNCH] = isAppStartup.toString()
        }
    }

    suspend fun getAppStartupFlag(): Boolean {
        return context.dataStore.data.first()[APP_LAUNCH].toBoolean()
    }






}