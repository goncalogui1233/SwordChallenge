package com.goncalo.swordchallenge.domain.model.classes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Class to be used to manipulate the Cat information throughout the application
 * (database, views)
 */
@Entity
data class CatInformation(
    @PrimaryKey(autoGenerate = false) val id: String,
    val breedId: String,
    val url: String,
    val breedName: String,
    val temperament: String,
    val origin: String,
    val description: String,
    @ColumnInfo(name = "life_span") val lifeSpan: String,

    val isFavourite: Boolean = false
)

fun List<CatApiInformation>.toCatInformationList(): List<CatInformation> {
    val catInformationList = arrayListOf<CatInformation>()
    this.forEach { catApiInformation ->
        catInformationList.add(catApiInformation.toCatInformation())
    }

    return catInformationList
}


fun CatApiInformation.toCatInformation() = CatInformation(
    id = this.id,
    breedId = this.id,
    url = this.url,
    breedName = this.breeds.first().name,
    temperament = this.breeds.first().temperament,
    origin = this.breeds.first().origin,
    description = this.breeds.first().description,
    lifeSpan = this.breeds.first().lifeSpan
)


