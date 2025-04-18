package com.goncalo.swordchallenge.domain.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.goncalo.swordchallenge.data.db.CatInformationDao
import com.goncalo.swordchallenge.data.network.CatInformationApi
import com.goncalo.swordchallenge.data.paging.CatRemoteMediator
import com.goncalo.swordchallenge.data.repository.CatInformationRepository
import com.goncalo.swordchallenge.domain.model.CatInformation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CatInformationRepositoryImpl @Inject constructor(
    private val catInformationApi: CatInformationApi,
    private val catInformationDao: CatInformationDao
) : CatInformationRepository {

    private val remoteMediator = CatRemoteMediator(catInformationApi, catInformationDao)

    @OptIn(ExperimentalPagingApi::class)
    override fun getCatList(breedName: String): Flow<PagingData<CatInformation>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = if (breedName.isEmpty()) remoteMediator else null
        ) {
            catInformationDao.getAllCatsPaging(breedName)
        }.flow
    }
}