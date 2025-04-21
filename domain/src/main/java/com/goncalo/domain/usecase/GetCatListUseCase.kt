package com.goncalo.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.goncalo.domain.repository.CatInformationRepository
import com.goncalo.domain.model.classes.CatInformation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCatListUseCase @Inject constructor(
    private val catInformationRepository: CatInformationRepository
){

    suspend operator fun invoke(breedName: String) : Flow<PagingData<CatInformation>> {
        val catFavouriteList = catInformationRepository.getCatFavouriteList()

        return catInformationRepository.getCatList(breedName).map { pagingData ->
            pagingData.map { cat ->
                val isFavourite = catFavouriteList.any { fav -> fav.id == cat.id }
                cat.copy(isFavourite = isFavourite)
            }
        }
    }

}