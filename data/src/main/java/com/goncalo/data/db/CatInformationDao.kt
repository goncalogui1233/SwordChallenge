package com.goncalo.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.goncalo.data.mappers.CatBreedInformation

@Dao
interface CatInformationDao {

    @Query("SELECT * FROM catbreedinformation")
    suspend fun getAllCats(): List<CatBreedInformation>

    @Query("SELECT * FROM catbreedinformation")
    fun getAllCatsPaging(): PagingSource<Int, CatBreedInformation>

    @Query("SELECT * FROM catbreedinformation where (name like '%' || :breedName || '%' or :breedName == '') ")
    fun getAllCatsByQuery(breedName: String): List<CatBreedInformation>

    @Query("SELECT * FROM catbreedinformation where id = :catId")
    fun getCatBreedById(catId: String): CatBreedInformation?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCat(catInformation: CatBreedInformation)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCatList(catInformationList: List<CatBreedInformation>)

    @Update
    suspend fun updateCat(catInformation: CatBreedInformation)

    @Delete
    suspend fun deleteCat(catInformation: CatBreedInformation)

    @Query("DELETE FROM catbreedinformation")
    suspend fun clearCatInformationTable()

}