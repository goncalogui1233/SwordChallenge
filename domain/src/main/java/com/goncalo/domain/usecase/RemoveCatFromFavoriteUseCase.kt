package com.goncalo.domain.usecase

import com.goncalo.domain.repository.CatInformationRepository
import com.goncalo.domain.model.classes.CatInformation
import javax.inject.Inject

class RemoveCatFromFavoriteUseCase @Inject constructor(
    private val catInformationRepository: CatInformationRepository
) {

    suspend operator fun invoke(catInformation: CatInformation) =
        catInformationRepository.deleteCatFavourite(catInformation.copy(isFavourite = false))

}