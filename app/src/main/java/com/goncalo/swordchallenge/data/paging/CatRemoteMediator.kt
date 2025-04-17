package com.goncalo.swordchallenge.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.goncalo.swordchallenge.data.db.CatInformationDao
import com.goncalo.swordchallenge.data.network.CatInformationApi
import com.goncalo.swordchallenge.domain.model.CatInformation
import com.goncalo.swordchallenge.domain.model.toCatInformationList

@OptIn(ExperimentalPagingApi::class)
class CatRemoteMediator(
    private val catInformationApi: CatInformationApi,
    private val catInformationDao: CatInformationDao,
    private val pageLimit: Int = 10,
) : RemoteMediator<Int, CatInformation>() {

    var currentPage = 0

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CatInformation>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    currentPage = 0
                    0
                }

                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> ++currentPage
            }

            val response = catInformationApi.getCatList(page = loadKey, limit = pageLimit)

            response.body()?.let {
                catInformationDao.insertCatList(it.toCatInformationList())
                MediatorResult.Success(endOfPaginationReached = it.isEmpty())
            } ?: MediatorResult.Success(endOfPaginationReached = true)

        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}