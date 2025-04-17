package com.goncalo.swordchallenge.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.goncalo.swordchallenge.data.db.CatInformationDao
import com.goncalo.swordchallenge.domain.model.CatInformation

@Database(entities = [CatInformation::class], version = 1, exportSchema = false)
abstract class SwordDatabase: RoomDatabase() {
    abstract fun catInformationDao(): CatInformationDao
}