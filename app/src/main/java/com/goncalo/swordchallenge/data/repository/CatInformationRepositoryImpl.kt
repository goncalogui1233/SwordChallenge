package com.goncalo.swordchallenge.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.goncalo.swordchallenge.data.datastore.CatDataStore
import com.goncalo.swordchallenge.data.network.CatInformationApi
import com.goncalo.swordchallenge.data.paging.CatRemoteMediator
import com.goncalo.swordchallenge.database.SwordDatabase
import com.goncalo.swordchallenge.domain.model.classes.CatFavouriteInformation
import com.goncalo.swordchallenge.domain.model.classes.CatInformation
import com.goncalo.swordchallenge.domain.model.helpers.Status
import com.goncalo.swordchallenge.domain.repository.CatInformationRepository
import com.goncalo.swordchallenge.domain.model.classes.toCatInformation
import com.goncalo.swordchallenge.domain.model.enums.CatDetailRequestSource
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
        val favoriteList = db.catFavouriteDao().getAllFavouriteCats()

        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = remoteMediator
        ) {
            db.catInformationDao().getAllCatsPaging(breedName)
        }.flow.map { pagingData ->
            pagingData.map { catInfo ->
                val isFavourite = favoriteList.any { fav -> fav.catInformation.id == catInfo.id }
                catInfo.copy(isFavourite = isFavourite)
            }
        }
    }

    override suspend fun getCatFavouriteList() = db.catFavouriteDao().getAllFavouriteCats()

    override fun getCatFavouriteListFlow(): Flow<List<CatFavouriteInformation>> = db.catFavouriteDao().getAllFavouriteCatsFlow()

    override suspend fun insertCatFavourite(catFavouriteInformation: CatFavouriteInformation): Status<Long> {
        return try {
            val insertedRowId = db.catFavouriteDao().insertNewFavourite(catFavouriteInformation)
            db.catInformationDao().updateCat(catFavouriteInformation.catInformation)
            Status(isSuccess = true, content = insertedRowId)
        } catch (e: Exception) {
            Status(isSuccess = false)
        }
    }

    override suspend fun deleteCatFavourite(catInformation: CatInformation): Status<Long> {
        return try {
            val removedRows = db.catFavouriteDao().removeFavourite(catInformation.id)
            if (removedRows > 0) {
                db.catInformationDao().updateCat(catInformation)
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
                CatDetailRequestSource.BREED_LIST -> db.catInformationDao().getCatBreedById(imageId)
                CatDetailRequestSource.FAVOURITE_LIST -> db.catFavouriteDao()
                    .getFavouriteCatById(imageId)?.catInformation
            }

        } catch (e: Exception) {
            null
        }
    }


}