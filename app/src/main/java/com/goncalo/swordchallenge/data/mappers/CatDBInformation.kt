package com.goncalo.swordchallenge.data.mappers

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.goncalo.swordchallenge.domain.model.classes.CatInformation

/**
 * Class that maps the information to be saved in the database related to the cat information loaded from the API.
 */
@Entity
data class CatDBInformation(
    @PrimaryKey(autoGenerate = false) val id: String,
    val breedId: String,
    val url: String,
    val breedName: String?,
    val temperament: String?,
    val origin: String?,
    val description: String?,
    @ColumnInfo(name = "life_span") val lifeSpan: String?,

    val isFavourite: Boolean = false
)

fun CatDBInformation.toCatInformation() = CatInformation(
    id = this.id,
    breedId = this.breedId,
    url = this.url,
    breedName = this.breedName,
    temperament = this.temperament,
    origin = this.origin,
    description = this.description,
    lifeSpan = this.lifeSpan,
    isFavourite = this.isFavourite
)

fun CatInformation.toCatDBInformation() = CatDBInformation(
    id = this.id,
    breedId = this.breedId,
    url = this.url,
    breedName = this.breedName,
    temperament = this.temperament,
    origin = this.origin,
    description = this.description,
    lifeSpan = this.lifeSpan,
    isFavourite = this.isFavourite
)