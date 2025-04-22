package com.goncalo.data.mappers

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.goncalo.domain.model.classes.CatInformation
import com.google.gson.annotations.SerializedName

/**
 * Class that maps the information to be saved in the database related to the cat information loaded from the API.
 */
@Entity
data class CatBreedInformation(
    @PrimaryKey(autoGenerate = false) val id: String,
    @SerializedName("reference_image_id") val imageId: String? = null,
    val name: String?,
    val temperament: String?,
    val origin: String?,
    val description: String?,
    @ColumnInfo(name = "life_span")
    @SerializedName("life_span")
    val lifeSpan: String?,

    val isFavourite: Boolean = false
)

fun CatBreedInformation.toCatInformation() = CatInformation(
    id = this.id,
    url = this.imageId,
    breedName = this.name,
    temperament = this.temperament,
    origin = this.origin,
    description = this.description,
    lifeSpan = this.lifeSpan,
    isFavourite = this.isFavourite
)

fun CatInformation.toCatDBInformation() = CatBreedInformation(
    id = this.id,
    imageId = this.url,
    name = this.breedName,
    temperament = this.temperament,
    origin = this.origin,
    description = this.description,
    lifeSpan = this.lifeSpan,
    isFavourite = this.isFavourite
)