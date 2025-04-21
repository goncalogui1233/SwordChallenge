package com.goncalo.swordchallenge.presentation.catdetail.viewmodel

import androidx.lifecycle.viewModelScope
import com.goncalo.domain.model.classes.CatInformation
import com.goncalo.domain.model.enums.CatDetailRequestSource
import com.goncalo.domain.usecase.AddCatToFavoriteUseCase
import com.goncalo.domain.usecase.GetCatDetailsByIdUseCase
import com.goncalo.domain.usecase.GetCatFavouriteDetailsUseCase
import com.goncalo.domain.usecase.RemoveCatFromFavoriteUseCase
import com.goncalo.swordchallenge.presentation.BaseViewModel
import com.goncalo.swordchallenge.presentation.common.helpers.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatDetailViewModel @Inject constructor(
    private val addCatToFavoriteUseCase: AddCatToFavoriteUseCase,
    private val removeCatFromFavoriteUseCase: RemoveCatFromFavoriteUseCase,
    private val getCatDetailsByIdUseCase: GetCatDetailsByIdUseCase,
    private val getCatFavouriteDetailsUseCase: GetCatFavouriteDetailsUseCase
) : BaseViewModel(addCatToFavoriteUseCase, removeCatFromFavoriteUseCase) {

    private val _uiState = MutableStateFlow<UIState<CatInformation>>(UIState.Loading)
    val uiState = _uiState.asStateFlow()

    suspend fun getCatDetails(imageId: String, detailSource: CatDetailRequestSource) = viewModelScope.launch (Dispatchers.IO){
        _uiState.value = UIState.Loading
        val response = when (detailSource) {
            CatDetailRequestSource.BREED_LIST -> getCatDetailsByIdUseCase(imageId)
            CatDetailRequestSource.FAVOURITE_LIST -> getCatFavouriteDetailsUseCase(imageId)
        }

        if(response.isSuccess) {
            _uiState.value = UIState.Success(response.content)
        } else {
            _uiState.value = UIState.Error(response.errorMessage)
        }
    }

    fun updateCatFavourite(catInformation: CatInformation) = viewModelScope.launch (Dispatchers.IO){
        val result = setCatFavouriteStatus(catInformation)

        if(result.isSuccess) {
            val currentData = (_uiState.value as UIState.Success).data
            currentData?.let {
                _uiState.value = UIState.Success(currentData.copy(isFavourite = !currentData.isFavourite))
            }
        }
    }

}