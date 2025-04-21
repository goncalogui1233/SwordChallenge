package com.goncalo.swordchallenge.domain.usecase

import com.goncalo.swordchallenge.domain.repository.CatInformationRepository
import com.goncalo.swordchallenge.data.mappers.CatDBFavouriteInformation
import com.goncalo.swordchallenge.domain.model.classes.CatInformation
import com.goncalo.swordchallenge.domain.model.helpers.Status
import javax.inject.Inject

class AddCatToFavoriteUseCase @Inject constructor(
    private val catInformationRepository: CatInformationRepository
) {

    suspend operator fun invoke(catInformation: CatInformation): Status<Long> {
        return catInformationRepository.insertCatFavourite(catInformation.copy(isFavourite = true))
    }

}