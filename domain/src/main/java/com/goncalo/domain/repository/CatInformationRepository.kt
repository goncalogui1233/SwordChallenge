package com.goncalo.domain.repository

import androidx.paging.PagingData
import com.goncalo.domain.model.classes.CatInformation
import com.goncalo.domain.model.enums.CatDetailRequestSource
import com.goncalo.domain.model.helpers.Status
import kotlinx.coroutines.flow.Flow

interface CatInformationRepository {

    suspend fun getCatList(breedName: String): Flow<PagingData<CatInformation>>

    suspend fun getCatFavouriteList(): List<CatInformation>

    fun getCatFavouriteListFlow(): Flow<List<CatInformation>>

    suspend fun insertCatFavourite(catFavouriteInformation: CatInformation): Status<Long>

    suspend fun deleteCatFavourite(catInformation: CatInformation): Status<Long>

    suspend fun getCatDetails(imageId: String, detailSource: CatDetailRequestSource): Status<CatInformation>


}