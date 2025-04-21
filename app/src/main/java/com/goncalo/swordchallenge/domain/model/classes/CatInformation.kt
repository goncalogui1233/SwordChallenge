package com.goncalo.swordchallenge.domain.model.classes

/**
 * Domain class that will transport the cat information between layers (data, presentation, domain)
 */
data class CatInformation(
    val id: String,
    val breedId: String,
    val url: String,
    val breedName: String?,
    val temperament: String?,
    val origin: String?,
    val description: String?,
    val lifeSpan: String?,

    val isFavourite: Boolean = false
)




