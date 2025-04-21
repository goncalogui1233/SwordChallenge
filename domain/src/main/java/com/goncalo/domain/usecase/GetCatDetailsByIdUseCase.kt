package com.goncalo.domain.usecase

import com.goncalo.domain.model.enums.CatDetailRequestSource
import com.goncalo.domain.repository.CatInformationRepository
import javax.inject.Inject

class GetCatDetailsByIdUseCase @Inject constructor(
    private val catInformationApi: CatInformationRepository
) {

    suspend operator fun invoke(imageId: String) =
        catInformationApi.getCatDetails(imageId, CatDetailRequestSource.BREED_LIST)

}