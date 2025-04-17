package com.goncalo.swordchallenge.presentation.catlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.goncalo.swordchallenge.data.repository.CatInformationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CatListViewModel @Inject constructor(
    private val catInformationRepository: CatInformationRepository
) : ViewModel() {

    val catList = catInformationRepository.getCatList().cachedIn(viewModelScope)

}