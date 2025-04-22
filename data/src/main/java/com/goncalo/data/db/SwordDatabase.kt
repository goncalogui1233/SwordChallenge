package com.goncalo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.goncalo.data.mappers.CatDBFavouriteInformation
import com.goncalo.data.mappers.CatBreedInformation
import com.goncalo.data.mappers.CatImageInformation

@Database(
    entities = [CatBreedInformation::class, CatDBFavouriteInformation::class, CatImageInformation::class],
    version = 1,
    exportSchema = false
)
abstract class SwordDatabase : RoomDatabase() {
    abstract fun catInformationDao(): CatInformationDao
    abstract fun catFavouriteDao(): CatFavouriteDao
    abstract fun catImageDao(): CatImagesDao
}