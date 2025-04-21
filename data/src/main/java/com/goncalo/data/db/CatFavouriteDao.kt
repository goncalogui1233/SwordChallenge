package com.goncalo.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.goncalo.data.mappers.CatDBFavouriteInformation
import kotlinx.coroutines.flow.Flow

@Dao
interface CatFavouriteDao {

    @Query("SELECT * FROM catfavouriteinformation")
    suspend fun getAllFavouriteCats(): List<CatDBFavouriteInformation>

    @Query("SELECT * FROM catfavouriteinformation")
    fun getAllFavouriteCatsFlow(): Flow<List<CatDBFavouriteInformation>>

    @Query("Select * from catfavouriteinformation where id = :catId")
    fun getFavouriteCatById(catId: String): CatDBFavouriteInformation?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewFavourite(favourite: CatDBFavouriteInformation): Long

    @Query("DELETE FROM catfavouriteinformation where id = :catId")
    suspend fun removeFavourite(catId: String): Int

}