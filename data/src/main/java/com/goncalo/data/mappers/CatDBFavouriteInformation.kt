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
    val catDBInformation: CatDBInformation
)

fun List<CatDBFavouriteInformation>.toCatInformationList(): List<CatInformation> =
    this.map { it.toCatInformation() }

fun CatDBFavouriteInformation.toCatInformation() = CatInformation(
    id = this.catDBInformation.id,
    breedId = this.catDBInformation.breedId,
    url = this.catDBInformation.url,
    breedName = this.catDBInformation.breedName,
    temperament = this.catDBInformation.temperament,
    origin = this.catDBInformation.origin,
    description = this.catDBInformation.description,
    lifeSpan = this.catDBInformation.lifeSpan,
    isFavourite = this.catDBInformation.isFavourite
)

fun CatInformation.toCatDBFavouriteInformation() = CatDBFavouriteInformation(
    favId = null,
    catDBInformation = CatDBInformation(
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
)


