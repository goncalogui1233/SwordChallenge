package com.goncalo.swordchallenge.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.goncalo.swordchallenge.domain.model.CatInformation

@Dao
interface CatInformationDao {

    @Query("SELECT * FROM catinformation")
    fun getAllCatsPaging(): PagingSource<Int, CatInformation>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCat(catInformation: CatInformation)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCatList(catInformationList: List<CatInformation>)

    @Delete
    suspend fun deleteCat(catInformation: CatInformation)

    @Query("DELETE FROM catinformation")
    suspend fun clearCatInformationTable()

}