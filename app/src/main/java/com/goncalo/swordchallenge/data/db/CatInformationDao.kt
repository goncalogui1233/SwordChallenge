package com.goncalo.swordchallenge.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.goncalo.swordchallenge.data.mappers.CatDBInformation
import com.goncalo.domain.model.classes.CatInformation

@Dao
interface CatInformationDao {

    @Query("SELECT * FROM catdbinformation")
    suspend fun getAllCats(): List<CatDBInformation>

    @Query("SELECT * FROM catdbinformation where (breedName like '%' || :breedName || '%' or :breedName == '') ")
    fun getAllCatsPaging(breedName: String): PagingSource<Int, CatDBInformation>

    @Query("SELECT * FROM catdbinformation where id = :catId")
    fun getCatBreedById(catId: String): CatDBInformation?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCat(catInformation: CatDBInformation)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCatList(catInformationList: List<CatDBInformation>)

    @Update
    suspend fun updateCat(catInformation: CatDBInformation)

    @Delete
    suspend fun deleteCat(catInformation: CatDBInformation)

    @Query("DELETE FROM catdbinformation")
    suspend fun clearCatInformationTable()

}