package com.goncalo.swordchallenge.domain.usecase

import com.goncalo.swordchallenge.data.repository.CatInformationRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCatFavouriteListUseCase @Inject constructor(
    private val catInformationRepository: CatInformationRepository
) {

    operator fun invoke() = catInformationRepository.getCatFavouriteListFlow().map { paging ->
        paging.map { catFavourite ->
            catFavourite.catInformation
        }
    }

}