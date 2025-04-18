package com.goncalo.swordchallenge.domain.usecase

import com.goncalo.swordchallenge.data.repository.CatInformationRepository
import com.goncalo.swordchallenge.domain.model.CatFavouriteInformation
import com.goncalo.swordchallenge.domain.model.CatInformation
import javax.inject.Inject

class AddCatToFavoriteUseCase @Inject constructor(
    private val catInformationRepository: CatInformationRepository
) {

    suspend operator fun invoke(catInformation: CatInformation) {
        val catFavourite = CatFavouriteInformation(catInformation = catInformation.copy(isFavourite = true))
        catInformationRepository.insertCatFavourite(catFavourite)
    }

}