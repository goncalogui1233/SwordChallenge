package com.goncalo.swordchallenge.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.goncalo.swordchallenge.data.mappers.CatDBFavouriteInformation
import com.goncalo.swordchallenge.data.mappers.CatDBInformation

@Database(entities = [CatDBInformation::class, CatDBFavouriteInformation::class], version = 1, exportSchema = false)
abstract class SwordDatabase: RoomDatabase() {
    abstract fun catInformationDao(): CatInformationDao
    abstract fun catFavouriteDao(): CatFavouriteDao
}