package com.goncalo.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.goncalo.data.datastore.CatDataStore
import com.goncalo.data.db.CatInformationDao
import com.goncalo.data.mappers.CatBreedInformation
import com.goncalo.data.network.CatInformationApi

@OptIn(ExperimentalPagingApi::class)
class CatRemoteMediator(
    private val catInformationApi: CatInformationApi,
    private val catInformationDao: CatInformationDao,
    private val catDataStore: CatDataStore,
    private val pageLimit: Int = 10,
) : RemoteMediator<Int, CatBreedInformation>() {

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
        state: PagingState<Int, CatBreedInformation>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    currentPage = 0
                    0
                }

                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> ++currentPage
            }

            val response = catInformationApi.getCatList(page = loadKey, limit = pageLimit)

            response.body()?.let {
                //Refresh action, reset table
                if(loadType == LoadType.REFRESH) {
                    catInformationDao.clearCatInformationTable()
                }

                //Fetch endpoint to get url for cat image
                val listWithImages = it.map { cat ->
                    cat.imageId?.let {
                        val catImage = catInformationApi.getCatImage(cat.imageId)
                        cat.copy(imageId = catImage.body()?.url)
                    } ?: cat
                }

                catInformationDao.insertCatList(listWithImages)
                catDataStore.saveCatListLastPage(currentPage.toString())
                MediatorResult.Success(endOfPaginationReached = listWithImages.isEmpty())
            } ?: kotlin.run {
                MediatorResult.Success(endOfPaginationReached = true)
            }

        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}