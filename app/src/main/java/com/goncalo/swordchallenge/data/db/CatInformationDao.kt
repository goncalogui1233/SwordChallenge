package com.goncalo.swordchallenge.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.goncalo.swordchallenge.domain.model.CatInformation

@Dao
interface CatInformationDao {

    @Query("SELECT * FROM catinformation")
    suspend fun getAllCats(): List<CatInformation>

    @Query("SELECT * FROM catinformation where (breedName like '%' || :breedName || '%' or :breedName == '') ")
    fun getAllCatsPaging(breedName: String): PagingSource<Int, CatInformation>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCat(catInformation: CatInformation)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCatList(catInformationList: List<CatInformation>)

    @Update
    suspend fun updateCat(catInformation: CatInformation)

    @Delete
    suspend fun deleteCat(catInformation: CatInformation)

    @Query("DELETE FROM catinformation")
    suspend fun clearCatInformationTable()

}