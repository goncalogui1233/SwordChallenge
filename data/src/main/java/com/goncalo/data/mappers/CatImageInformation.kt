package com.goncalo.data.mappers

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.goncalo.data.db.SwordDatabase
import com.goncalo.data.network.CatInformationApi

/**
 * Class to be used when getting cat image information from the API
 */
@Entity
data class CatImageInformation(
    @PrimaryKey(autoGenerate = false) val id: String,
    val url: String? = null,
)