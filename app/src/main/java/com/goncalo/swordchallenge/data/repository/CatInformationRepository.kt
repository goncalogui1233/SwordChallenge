package com.goncalo.swordchallenge.data.repository

import androidx.paging.PagingData
import com.goncalo.swordchallenge.domain.model.CatInformation
import kotlinx.coroutines.flow.Flow

interface CatInformationRepository {

    fun getCatList(): Flow<PagingData<CatInformation>>

}