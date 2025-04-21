package com.goncalo.data.mappers

import com.goncalo.domain.model.classes.CatInformation
import com.google.gson.annotations.SerializedName

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

data class Breed(
    val weight: Weight,
    val id: String,
    val name: String,
    val temperament: String,
    val origin: String,
    val description: String,
    @SerializedName("life_span") val lifeSpan: String,
)

data class Weight(
    val imperial: String,
    val metric: String
)

fun List<CatApiInformation>.toCatDBInformationList(): List<CatDBInformation> {
    val catInformationList = arrayListOf<CatDBInformation>()
    this.forEach { catApiInformation ->
        catInformationList.add(catApiInformation.toCatDBInformation())
    }

    return catInformationList
}

fun CatApiInformation.toCatDBInformation() = CatDBInformation(
    id = this.id,
    breedId = this.id,
    url = this.url,
    breedName = this.breeds.firstOrNull()?.name,
    temperament = this.breeds.firstOrNull()?.temperament,
    origin = this.breeds.firstOrNull()?.origin,
    description = this.breeds.firstOrNull()?.description,
    lifeSpan = this.breeds.firstOrNull()?.lifeSpan
)

fun CatApiInformation.toCatInformation() = CatInformation(
    id = this.id,
    breedId = this.id,
    url = this.url,
    breedName = this.breeds.firstOrNull()?.name,
    temperament = this.breeds.firstOrNull()?.temperament,
    origin = this.breeds.firstOrNull()?.origin,
    description = this.breeds.firstOrNull()?.description,
    lifeSpan = this.breeds.firstOrNull()?.lifeSpan
)