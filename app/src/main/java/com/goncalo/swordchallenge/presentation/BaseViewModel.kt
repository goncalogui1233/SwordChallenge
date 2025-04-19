package com.goncalo.swordchallenge.presentation

import androidx.lifecycle.ViewModel
import com.goncalo.swordchallenge.domain.model.classes.CatInformation
import com.goncalo.swordchallenge.domain.usecase.AddCatToFavoriteUseCase
import com.goncalo.swordchallenge.domain.usecase.RemoveCatFromFavoriteUseCase

open class BaseViewModel(
    private val addCatToFavoriteUseCase: AddCatToFavoriteUseCase,
    private val removeCatFromFavoriteUseCase: RemoveCatFromFavoriteUseCase
) : ViewModel() {

    suspend fun setCatFavouriteStatus(catInformation: CatInformation) =
        if (catInformation.isFavourite) {
            removeCatFromFavoriteUseCase(catInformation)
        } else {
            addCatToFavoriteUseCase(catInformation)
        }


}