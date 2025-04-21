package com.goncalo.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.goncalo.data.datastore.CatDataStore
import com.goncalo.data.network.CatInformationApi
import com.goncalo.data.paging.CatRemoteMediator
import com.goncalo.data.db.SwordDatabase
import com.goncalo.data.mappers.toCatDBFavouriteInformation
import com.goncalo.data.mappers.toCatDBInformation
import com.goncalo.data.mappers.toCatInformation
import com.goncalo.data.mappers.toCatInformationList
import com.goncalo.domain.model.classes.CatInformation
import com.goncalo.domain.model.helpers.Status
import com.goncalo.domain.repository.CatInformationRepository
import com.goncalo.domain.model.enums.CatDetailRequestSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CatInformationRepositoryImpl @Inject constructor(
    private val catInformationApi: CatInformationApi,
    private val db: SwordDatabase,
    private val dataStore: CatDataStore
) : CatInformationRepository {

    private val remoteMediator = CatRemoteMediator(catInformationApi, db.catInformationDao(), dataStore)

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getCatList(breedName: String): Flow<PagingData<CatInformation>> {
        //Gets favourite list to match with the cat list displayed on screen
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = remoteMediator
        ) {
            db.catInformationDao().getAllCatsPaging(breedName)
        }.flow.map { pagingData ->
            pagingData.map { catInfo ->
                catInfo.toCatInformation()
            }
        }
    }

    override suspend fun getCatFavouriteList(): List<CatInformation> =
        db.catFavouriteDao().getAllFavouriteCats().toCatInformationList()

    override fun getCatFavouriteListFlow(): Flow<List<CatInformation>> =
        db.catFavouriteDao().getAllFavouriteCatsFlow().map { it.toCatInformationList() }

    override suspend fun insertCatFavourite(catFavouriteInformation: CatInformation): Status<Long> {
        return try {
            val insertedRowId = db.catFavouriteDao().insertNewFavourite(catFavouriteInformation.toCatDBFavouriteInformation())
            db.catInformationDao().updateCat(catFavouriteInformation.toCatDBInformation())
            Status(isSuccess = true, content = insertedRowId)
        } catch (e: Exception) {
            Status(isSuccess = false)
        }
    }

    override suspend fun deleteCatFavourite(catInformation: CatInformation): Status<Long> {
        return try {
            val removedRows = db.catFavouriteDao().removeFavourite(catInformation.id)
            if (removedRows > 0) {
                db.catInformationDao().updateCat(catInformation.toCatDBInformation())
            }

            Status(isSuccess = removedRows > 0, content = removedRows.toLong())
        } catch (e: Exception) {
            Status(isSuccess = false)
        }
    }

    override suspend fun getCatDetails(imageId: String, detailSource: CatDetailRequestSource): Status<CatInformation> {
        return try {
            val response = catInformationApi.getCatDetails(imageId)

            if(response.isSuccessful) {
                Status(isSuccess = true, content = response.body()?.toCatInformation())
            } else {
                val catDetailsDB = getCatDetailsFromDB(imageId, detailSource)
                Status(isSuccess = catDetailsDB != null, content = catDetailsDB)
            }
        } catch (e: Exception) {
            val catDetailsDB = getCatDetailsFromDB(imageId, detailSource)
            Status(isSuccess = catDetailsDB != null, content = catDetailsDB)
        }
    }


    private fun getCatDetailsFromDB(
        imageId: String,
        detailSource: CatDetailRequestSource
    ): CatInformation? {
        return try {
            when (detailSource) {
                CatDetailRequestSource.BREED_LIST -> db.catInformationDao().getCatBreedById(imageId)?.toCatInformation()
                CatDetailRequestSource.FAVOURITE_LIST -> db.catFavouriteDao()
                    .getFavouriteCatById(imageId)?.toCatInformation()
            }

        } catch (e: Exception) {
            null
        }
    }


}