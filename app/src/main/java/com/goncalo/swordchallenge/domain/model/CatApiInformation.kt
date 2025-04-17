package com.goncalo.swordchallenge.domain.model

/**
 * Class to be used when getting cat information from the API
 */
data class CatApiInformation(
    val id: String,
    val url: String,
    val width: Int,
    val height: Int,
    val breeds: List<Breed>
)