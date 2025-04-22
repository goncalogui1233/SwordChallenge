package com.goncalo.data.mappers

import com.goncalo.domain.model.classes.CatInformation
import com.google.gson.annotations.SerializedName

/**
 * Class to be used when getting cat image information from the API
 */
data class CatApiInformation(
    val id: String,
    val url: String,
)