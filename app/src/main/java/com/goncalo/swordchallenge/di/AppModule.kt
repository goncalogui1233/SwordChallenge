package com.goncalo.swordchallenge.di

import android.content.Context
import androidx.room.Room
import com.goncalo.swordchallenge.common.API_BASE_URL
import com.goncalo.swordchallenge.common.DB_NAME
import com.goncalo.data.datastore.CatDataStore
import com.goncalo.data.network.CatInformationApi
import com.goncalo.data.repository.AppSettingsRepositoryImpl
import com.goncalo.domain.repository.CatInformationRepository
import com.goncalo.data.db.SwordDatabase
import com.goncalo.data.repository.CatInformationRepositoryImpl
import com.goncalo.domain.repository.AppSettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    @Provides
    @Singleton
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, SwordDatabase::class.java, DB_NAME).build()

    @Provides
    @Singleton
    fun provideCatApi(retrofit: Retrofit) = retrofit.create(CatInformationApi::class.java)

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context) =
        CatDataStore(context)

    @Provides
    @Singleton
    fun provideCatInformationRepository(
        api: CatInformationApi,
        db: SwordDatabase,
        dataStore: CatDataStore
    ): CatInformationRepository =
        CatInformationRepositoryImpl(api, db, dataStore)

    @Provides
    @Singleton
    fun provideAppSettingsRepository(dataStore: CatDataStore): AppSettingsRepository =
        AppSettingsRepositoryImpl(dataStore)

}