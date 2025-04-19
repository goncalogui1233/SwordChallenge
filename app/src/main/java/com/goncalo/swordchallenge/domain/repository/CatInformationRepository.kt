package com.goncalo.swordchallenge.domain.repository

import androidx.paging.PagingData
import com.goncalo.swordchallenge.domain.model.classes.CatFavouriteInformation
import com.goncalo.swordchallenge.domain.model.classes.CatInformation
import com.goncalo.swordchallenge.domain.model.enums.CatDetailRequestSource
import com.goncalo.swordchallenge.domain.model.helpers.Status
import kotlinx.coroutines.flow.Flow

interface CatInformationRepository {

    suspend fun getCatList(breedName: String): Flow<PagingData<CatInformation>>

    suspend fun getCatFavouriteList(): List<CatFavouriteInformation>

    fun getCatFavouriteListFlow(): Flow<List<CatFavouriteInformation>>

    suspend fun insertCatFavourite(catFavouriteInformation: CatFavouriteInformation): Status<Long>

    suspend fun deleteCatFavourite(catInformation: CatInformation): Status<Long>

    suspend fun getCatDetails(imageId: String, detailSource: CatDetailRequestSource): Status<CatInformation>


}