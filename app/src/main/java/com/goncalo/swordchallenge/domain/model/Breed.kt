package com.goncalo.swordchallenge.domain.model

import com.google.gson.annotations.SerializedName

data class Breed(
    val weight: Weight,
    val id: String,
    val name: String,
    val temperament: String,
    val origin: String,
    val description: String,
    @SerializedName("life_span") val lifeSpan: String,
    @SerializedName("cfa_url") val cfaUrl: String,
    @SerializedName("vetstreet_url") val vetstreetUrl: String,
    @SerializedName("vcahospitals_url") val vcahospitalsUrl: String,
    @SerializedName("country_codes") val countryCodes: String,
    @SerializedName("country_code") val countryCode: String,
)