package com.goncalo.swordchallenge.data.repository

import androidx.paging.PagingData
import com.goncalo.swordchallenge.domain.model.CatFavouriteInformation
import com.goncalo.swordchallenge.domain.model.CatInformation
import kotlinx.coroutines.flow.Flow

interface CatInformationRepository {

    suspend fun getCatList(breedName: String): Flow<PagingData<CatInformation>>

    suspend fun getCatFavouriteList(): List<CatFavouriteInformation>

    fun getCatFavouriteListFlow(): Flow<List<CatFavouriteInformation>>

    suspend fun insertCatFavourite(catFavouriteInformation: CatFavouriteInformation)

    suspend fun deleteCatFavourite(catInformation: CatInformation)


}