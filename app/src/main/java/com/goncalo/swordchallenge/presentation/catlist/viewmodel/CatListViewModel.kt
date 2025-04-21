package com.goncalo.swordchallenge.presentation.catlist.viewmodel

import androidx.lifecycle.viewModelScope
import com.goncalo.domain.model.classes.CatInformation
import com.goncalo.domain.usecase.AddCatToFavoriteUseCase
import com.goncalo.domain.usecase.GetCatFavouriteListUseCase
import com.goncalo.domain.usecase.GetCatListUseCase
import com.goncalo.domain.usecase.RemoveCatFromFavoriteUseCase
import com.goncalo.swordchallenge.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatListViewModel @Inject constructor(
    private val addCatToFavoriteUseCase: AddCatToFavoriteUseCase,
    private val removeCatFromFavoriteUseCase: RemoveCatFromFavoriteUseCase,
    private val getCatFavouriteListUseCase: GetCatFavouriteListUseCase,
    private val getCatListUseCase: GetCatListUseCase
) : BaseViewModel(addCatToFavoriteUseCase, removeCatFromFavoriteUseCase) {

    private var breedNameSearch = MutableStateFlow("")
    private var forceUpdateCatList = MutableStateFlow(false)

    //Combine search flow with force update flow to trigger refresh of the Pager
    private val combinedFlow = combine(breedNameSearch, forceUpdateCatList) { breedName, _ ->
        Pair(breedName, Unit)
    }

    val catList = combinedFlow.flatMapLatest { (breedName, _) ->
        getCatListUseCase(breedName)
    }

    val catFavouriteList = getCatFavouriteListUseCase.invoke()

    fun setBreedName(name: String) {
        breedNameSearch.value = name
    }

    fun changeCatFavouriteStatus(catInformation: CatInformation) = viewModelScope.launch {
        setCatFavouriteStatus(catInformation)

        //Force update the cat list to refresh the favourite status
        forceUpdateCatList.value = !forceUpdateCatList.value
    }


}