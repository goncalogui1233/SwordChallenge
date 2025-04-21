package com.goncalo.swordchallenge.domain.usecase

import com.goncalo.swordchallenge.domain.repository.CatInformationRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCatFavouriteListUseCase @Inject constructor(
    private val catInformationRepository: CatInformationRepository
) {

    operator fun invoke() = catInformationRepository.getCatFavouriteListFlow()

}