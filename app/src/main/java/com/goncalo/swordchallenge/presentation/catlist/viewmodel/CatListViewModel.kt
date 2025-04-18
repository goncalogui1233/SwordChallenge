package com.goncalo.swordchallenge.presentation.catlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.goncalo.swordchallenge.data.repository.CatInformationRepository
import com.goncalo.swordchallenge.domain.model.CatInformation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatListViewModel @Inject constructor(
    private val catInformationRepository: CatInformationRepository
) : ViewModel() {

    private var breedName = MutableStateFlow("")

    val catList = breedName.flatMapLatest { b ->
        catInformationRepository.getCatList(b)
    }.cachedIn(viewModelScope)

    val catFavouriteList = catInformationRepository.getCatFavouriteList()

    fun setBreedName(name: String) {
        breedName.value = name
    }

    fun addCatToFavourite(catFavourite: CatInformation) =
        viewModelScope.launch(Dispatchers.IO) {
            catInformationRepository.insertCatFavourite(catFavourite)
        }

    fun deleteCatFromFavourite(catFavourite: CatInformation) = viewModelScope.launch {
        catInformationRepository.deleteCatFavourite(catFavourite)
    }
}