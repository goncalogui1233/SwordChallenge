package com.goncalo.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.goncalo.data.mappers.CatImageInformation


@Dao
interface CatImagesDao {

    @Query("SELECT * FROM catimageinformation")
    suspend fun getListImages(): List<CatImageInformation>

    @Query("SELECT * FROM catimageinformation where id = :catId")
    suspend fun getImageById(catId: String): CatImageInformation?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCatImage(catImageInformation: CatImageInformation)

}