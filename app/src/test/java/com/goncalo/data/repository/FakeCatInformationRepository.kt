package com.goncalo.data.repository

import androidx.paging.PagingData
import com.goncalo.data.mappers.CatDBFavouriteInformation
import com.goncalo.data.mappers.toCatDBInformation
import com.goncalo.data.mappers.toCatInformation
import com.goncalo.data.mappers.toCatInformationList
import com.goncalo.domain.model.classes.CatInformation
import com.goncalo.domain.model.enums.CatDetailRequestSource
import com.goncalo.domain.model.helpers.Status
import com.goncalo.domain.repository.CatInformationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeCatInformationRepository : CatInformationRepository {

    private val breedList = arrayListOf(
        CatInformation(
            "abys",
            "https://cdn2.thecatapi.com/images/xnzzM6MBI.jpg",
            "Abyssinian",
            "Active, Energetic, Independent, Intelligent, Gentle",
            "Egypt",
            "Good cat",
            "14 - 15",
        ),
        CatInformation(
            "amis",
            "https://cdn2.thecatapi.com/images/zTiuUpmqf.jpg",
            "Australian Mist",
            "Lively, Social, Fun-loving, Relaxed, Affectionate",
            "Australia",
            "Good cat",
            "12 - 16",
        ),
        CatInformation(
            "bali",
            "https://cdn2.thecatapi.com/images/OaTQfIktG.jpg",
            "Balinese",
            "Affectionate, Intelligent, Playful",
            "United States",
            "Good cat",
            "10 - 15",
        )
    )

    private val favouriteList = arrayListOf<CatDBFavouriteInformation>()

    override suspend fun getCatList(): Flow<PagingData<CatInformation>> {
        return flowOf(PagingData.from(breedList))
    }

    override suspend fun getCatSearchList(breedName: String): List<CatInformation> {
        return breedList.filter { it.breedName.equals(breedName) }
    }

    override suspend fun getCatFavouriteList(): List<CatInformation> = favouriteList.toCatInformationList()

    override fun getCatFavouriteListFlow(): Flow<List<CatInformation>> {
        return flowOf(favouriteList.toCatInformationList())
    }

    override suspend fun insertCatFavourite(catFavouriteInformation: CatInformation): Status<Long> {
        if(favouriteList.any { it.toCatInformation().id == catFavouriteInformation.id }.not()) {
            favouriteList.add(
                CatDBFavouriteInformation(
                    favId = null,
                    catBreedInformation = catFavouriteInformation.toCatDBInformation().copy(isFavourite = true)
                )
            )

            val itemIndex = breedList.indexOfFirst { it.id == catFavouriteInformation.id }
            if(itemIndex != -1) {
                breedList.removeAt(itemIndex)
                breedList.add(itemIndex, catFavouriteInformation.copy(isFavourite = true))
            }

            return Status(isSuccess = true)
        }

        return Status(isSuccess = false)
    }

    override suspend fun deleteCatFavourite(catInformation: CatInformation): Status<Long> {
        val hasRemoved =
            favouriteList.remove(favouriteList.firstOrNull { it.toCatInformation().id == catInformation.id })
        return Status(isSuccess = hasRemoved)
    }

    override suspend fun getCatDetails(
        imageId: String,
        detailSource: CatDetailRequestSource
    ): Status<CatInformation> {

        if(detailSource == CatDetailRequestSource.BREED_LIST) {
            val item = breedList.firstOrNull { it.id == imageId }
            return Status(isSuccess = item != null, content = item)
        } else {
            val item = favouriteList.firstOrNull { it.toCatInformation().id == imageId }
            return Status(isSuccess = item != null, content = item?.toCatInformation())
        }
    }
}