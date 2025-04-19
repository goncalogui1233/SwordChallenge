package com.goncalo.swordchallenge.domain.model.helpers

data class Status<out T>(
    val isSuccess: Boolean,
    val content: T? = null,
    val errorMessage: String? = null
)