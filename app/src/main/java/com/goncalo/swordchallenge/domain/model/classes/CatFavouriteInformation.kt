package com.goncalo.swordchallenge.domain.model.classes

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Class used to save the cat information in a distinct table in order to not lose
 * the cat information when cat list is cleared.
 * It was needed a second object due to the fact that Room does not support that the
 * same object can be used in two different tables.
 */
@Entity
data class CatFavouriteInformation(
    @PrimaryKey(autoGenerate = true) val favId: Int? = null,
    @Embedded
    val catInformation: CatInformation
)