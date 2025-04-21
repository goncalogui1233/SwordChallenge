package com.goncalo.domain.usecase

import com.goncalo.domain.repository.CatInformationRepository
import com.goncalo.domain.model.classes.CatInformation
import com.goncalo.domain.model.helpers.Status
import javax.inject.Inject

class AddCatToFavoriteUseCase @Inject constructor(
    private val catInformationRepository: CatInformationRepository
) {

    suspend operator fun invoke(catInformation: CatInformation): Status<Long> {
        return catInformationRepository.insertCatFavourite(catInformation.copy(isFavourite = true))
    }

}