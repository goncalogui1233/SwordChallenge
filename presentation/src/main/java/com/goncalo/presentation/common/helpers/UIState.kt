package com.goncalo.presentation.common.helpers

sealed class UIState<out T> {
    data object Loading : UIState<Nothing>()
    data class Success<T>(val data: T? = null) : UIState<T>()
    data class Error(val message: String? = null) : UIState<Nothing>()
}