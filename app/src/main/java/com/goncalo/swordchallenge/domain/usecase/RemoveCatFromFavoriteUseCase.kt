package com.goncalo.swordchallenge.domain.usecase

import com.goncalo.swordchallenge.data.repository.CatInformationRepository
import com.goncalo.swordchallenge.domain.model.CatInformation
import javax.inject.Inject

class RemoveCatFromFavoriteUseCase @Inject constructor(
    private val catInformationRepository: CatInformationRepository
) {

    suspend operator fun invoke(catInformation: CatInformation) =
        catInformationRepository.deleteCatFavourite(catInformation)

}