package com.goncalo.presentation.catlist.viewmodel

import android.provider.Contacts.Intents.UI
import androidx.lifecycle.viewModelScope
import com.goncalo.domain.model.classes.CatInformation
import com.goncalo.domain.usecase.AddCatToFavoriteUseCase
import com.goncalo.domain.usecase.GetCatFavouriteListUseCase
import com.goncalo.domain.usecase.GetCatListUseCase
import com.goncalo.domain.usecase.GetSearchCatListUseCase
import com.goncalo.domain.usecase.RemoveCatFromFavoriteUseCase
import com.goncalo.presentation.BaseViewModel
import com.goncalo.presentation.common.helpers.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatListViewModel @Inject constructor(
    private val addCatToFavoriteUseCase: AddCatToFavoriteUseCase,
    private val removeCatFromFavoriteUseCase: RemoveCatFromFavoriteUseCase,
    private val getCatFavouriteListUseCase: GetCatFavouriteListUseCase,
    private val getCatListUseCase: GetCatListUseCase,
    private val getCatSearchListUseCase: GetSearchCatListUseCase
) : BaseViewModel(addCatToFavoriteUseCase, removeCatFromFavoriteUseCase) {

    //Cat List Params
    private var forceUpdateCatList = MutableStateFlow(false)
    val catList = forceUpdateCatList.flatMapLatest {
        getCatListUseCase()
    }

    //Cat Search List Params
    private var breedNameSearch = MutableStateFlow("")
    val catSearchListUiState: MutableStateFlow<UIState<List<CatInformation>>> = MutableStateFlow(UIState.Loading)

    //Cat Favourite List
    val catFavouriteList = getCatFavouriteListUseCase.invoke()

    init {
        //Setup search debounce to search
        viewModelScope.launch {
            breedNameSearch.debounce(500).distinctUntilChanged().collectLatest {
                if(it.isNotEmpty()) {
                    searchCatBreed(it)
                } else {
                    catSearchListUiState.value = UIState.Success(emptyList())
                }
            }
        }
    }

    fun changeCatFavouriteStatus(catInformation: CatInformation) = viewModelScope.launch {
        setCatFavouriteStatus(catInformation)

        //If empty, just send signal to refresh cat list
        if(breedNameSearch.value.isEmpty()) {
            forceUpdateCatList.value = !forceUpdateCatList.value
        } else {
            if(catSearchListUiState.value is UIState.Success) {
                val updatedList = (catSearchListUiState.value as UIState.Success).data?.map {
                    it.copy(isFavourite = it.id == catInformation.id)
                }

                catSearchListUiState.value = UIState.Success(updatedList)
            }
        }
    }

    fun setBreedSearchName(name: String) {
        breedNameSearch.value = name
    }

    private fun searchCatBreed(breedName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            catSearchListUiState.value = UIState.Loading
            val searchResult = getCatSearchListUseCase(breedName)
            catSearchListUiState.value = UIState.Success(searchResult)
        }
    }
}