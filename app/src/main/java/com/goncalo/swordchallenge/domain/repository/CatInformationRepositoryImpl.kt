package com.goncalo.swordchallenge.domain.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.goncalo.swordchallenge.data.network.CatInformationApi
import com.goncalo.swordchallenge.data.paging.CatRemoteMediator
import com.goncalo.swordchallenge.data.repository.CatInformationRepository
import com.goncalo.swordchallenge.database.SwordDatabase
import com.goncalo.swordchallenge.domain.model.CatFavouriteInformation
import com.goncalo.swordchallenge.domain.model.CatInformation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CatInformationRepositoryImpl @Inject constructor(
    private val catInformationApi: CatInformationApi,
    private val db: SwordDatabase,
) : CatInformationRepository {

    private val remoteMediator = CatRemoteMediator(catInformationApi, db.catInformationDao())

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getCatList(breedName: String): Flow<PagingData<CatInformation>> {
        //Gets favourite list to match with the cat list displayed on screen
        val favoriteList = db.catFavouriteDao().getAllFavouriteCats()

        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = if (breedName.isEmpty()) remoteMediator else null
        ) {
            db.catInformationDao().getAllCatsPaging(breedName)
        }.flow.map { pagingData ->
            pagingData.map { catInfo ->
                val isFavourite = favoriteList.any { fav -> fav.catInformation.id == catInfo.id }
                catInfo.copy(isFavourite = isFavourite)
            }
        }
    }

    override fun getCatFavouriteList() = db.catFavouriteDao().getAllFavouriteCatsFlow()

    override suspend fun insertCatFavourite(catFavouriteInformation: CatInformation) {
        val catFavourite = CatFavouriteInformation(catInformation = catFavouriteInformation)
        db.catFavouriteDao().insertNewFavourite(catFavourite)
    }

    override suspend fun deleteCatFavourite(catInformation: CatInformation) {
        db.catFavouriteDao().removeFavourite(catInformation.id)
    }
}