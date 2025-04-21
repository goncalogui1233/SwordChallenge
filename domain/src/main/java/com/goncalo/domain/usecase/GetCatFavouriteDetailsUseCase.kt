package com.goncalo.domain.usecase

import com.goncalo.domain.model.enums.CatDetailRequestSource
import com.goncalo.domain.repository.CatInformationRepository
import javax.inject.Inject

class GetCatFavouriteDetailsUseCase @Inject constructor(
    private val catInformationRepository: CatInformationRepository
) {

    suspend operator fun invoke(catId: String) =
        catInformationRepository.getCatDetails(catId, CatDetailRequestSource.FAVOURITE_LIST)

}