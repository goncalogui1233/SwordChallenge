package com.goncalo.swordchallenge.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.goncalo.swordchallenge.domain.model.CatFavouriteInformation
import kotlinx.coroutines.flow.Flow

@Dao
interface CatFavouriteDao {

    @Query("SELECT * FROM catfavouriteinformation")
    suspend fun getAllFavouriteCats(): List<CatFavouriteInformation>

    @Query("SELECT * FROM catfavouriteinformation")
    fun getAllFavouriteCatsFlow(): Flow<List<CatFavouriteInformation>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewFavourite(favourite: CatFavouriteInformation): Long

    @Query("DELETE FROM catfavouriteinformation where id = :catId")
    suspend fun removeFavourite(catId: String)

}