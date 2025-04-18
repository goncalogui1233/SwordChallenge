package com.goncalo.swordchallenge.domain.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
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
        val breed = catApiInformation.breeds.first()
        catInformationList.add(
            CatInformation(
                id = catApiInformation.id,
                breedId = breed.id,
                url = catApiInformation.url,
                breedName = breed.name,
                temperament = breed.temperament,
                origin = breed.origin,
                description = breed.description,
                lifeSpan = breed.lifeSpan
            )
        )
    }

    return catInformationList
}


