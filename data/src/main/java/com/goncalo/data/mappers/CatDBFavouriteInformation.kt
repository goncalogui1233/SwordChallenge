package com.goncalo.data.mappers

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.goncalo.domain.model.classes.CatInformation

/**
 * Class used to save the cat information in a distinct table in order to not lose
 * the favorite cat information when cat list is cleared.
 * It was needed a second object due to the fact that Room does not support that the
 * same object can be used in two different tables.
 */
@Entity(
    tableName = "catfavouriteinformation",
    indices = [Index(value = ["id"], unique = true)]
)
data class CatDBFavouriteInformation(
    @PrimaryKey(autoGenerate = true) val favId: Int? = null,
    @Embedded
    val catBreedInformation: CatBreedInformation
)

fun List<CatDBFavouriteInformation>.toCatInformationList(): List<CatInformation> =
    this.map { it.toCatInformation() }

fun CatDBFavouriteInformation.toCatInformation() = CatInformation(
    id = this.catBreedInformation.id,
    url = this.catBreedInformation.imageId,
    breedName = this.catBreedInformation.name,
    temperament = this.catBreedInformation.temperament,
    origin = this.catBreedInformation.origin,
    description = this.catBreedInformation.description,
    lifeSpan = this.catBreedInformation.lifeSpan,
    isFavourite = this.catBreedInformation.isFavourite
)

fun CatInformation.toCatDBFavouriteInformation() = CatDBFavouriteInformation(
    favId = null,
    catBreedInformation = CatBreedInformation(
        id = this.id,
        imageId = this.url,
        name = this.breedName,
        temperament = this.temperament,
        origin = this.origin,
        description = this.description,
        lifeSpan = this.lifeSpan,
        isFavourite = this.isFavourite
    )
)


