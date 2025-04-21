package com.goncalo.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.goncalo.data.datastore.CatDataStore
import com.goncalo.data.db.CatInformationDao
import com.goncalo.data.mappers.CatDBInformation
import com.goncalo.data.mappers.toCatDBInformationList
import com.goncalo.data.network.CatInformationApi
import com.goncalo.domain.model.classes.CatInformation

@OptIn(ExperimentalPagingApi::class)
class CatRemoteMediator(
    private val catInformationApi: CatInformationApi,
    private val catInformationDao: CatInformationDao,
    private val catDataStore: CatDataStore,
    private val pageLimit: Int = 10,
) : RemoteMediator<Int, CatDBInformation>() {

    private var currentPage = 0

    override suspend fun initialize(): InitializeAction {
        return if(catInformationDao.getAllCats().isEmpty() || catDataStore.getAppStartupFlag()) {
            catDataStore.saveAppStartupFlag(false)
            InitializeAction.LAUNCH_INITIAL_REFRESH
        } else {
            catDataStore.getCatListLastPage()?.let {
                currentPage = it.toInt()
                InitializeAction.SKIP_INITIAL_REFRESH
            } ?: kotlin.run {
                InitializeAction.LAUNCH_INITIAL_REFRESH //Lost last page, refresh all data
            }
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CatDBInformation>
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
                //Refresh action, reset table
                if(loadType == LoadType.REFRESH) {
                    catInformationDao.clearCatInformationTable()
                }

                catInformationDao.insertCatList(it.toCatDBInformationList())
                catDataStore.saveCatListLastPage(currentPage.toString())
                MediatorResult.Success(endOfPaginationReached = it.isEmpty())
            } ?: MediatorResult.Success(endOfPaginationReached = true)

        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}