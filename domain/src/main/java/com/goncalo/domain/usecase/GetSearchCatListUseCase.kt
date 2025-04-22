package com.goncalo.domain.usecase

import com.goncalo.domain.model.classes.CatInformation
import com.goncalo.domain.repository.CatInformationRepository
import javax.inject.Inject

class GetSearchCatListUseCase @Inject constructor(
    private val catInformationRepository: CatInformationRepository
) {

    suspend operator fun invoke(breedName: String): List<CatInformation> {
        val favouriteList = catInformationRepository.getCatFavouriteList()

        return catInformationRepository.getCatSearchList(breedName).map {
            val isFavourite = favouriteList.any { fav -> fav.id == it.id }
            it.copy(isFavourite = isFavourite)
        }
    }

}