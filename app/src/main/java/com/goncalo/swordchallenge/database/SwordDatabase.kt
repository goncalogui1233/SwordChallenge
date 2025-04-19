package com.goncalo.swordchallenge.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.goncalo.swordchallenge.data.db.CatFavouriteDao
import com.goncalo.swordchallenge.data.db.CatInformationDao
import com.goncalo.swordchallenge.domain.model.classes.CatFavouriteInformation
import com.goncalo.swordchallenge.domain.model.classes.CatInformation

@Database(entities = [CatInformation::class, CatFavouriteInformation::class], version = 1, exportSchema = false)
abstract class SwordDatabase: RoomDatabase() {
    abstract fun catInformationDao(): CatInformationDao
    abstract fun catFavouriteDao(): CatFavouriteDao
}