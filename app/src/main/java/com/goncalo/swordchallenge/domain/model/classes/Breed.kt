package com.goncalo.swordchallenge.domain.model.classes

import com.google.gson.annotations.SerializedName

data class Breed(
    val weight: Weight,
    val id: String,
    val name: String,
    val temperament: String,
    val origin: String,
    val description: String,
    @SerializedName("life_span") val lifeSpan: String,
)