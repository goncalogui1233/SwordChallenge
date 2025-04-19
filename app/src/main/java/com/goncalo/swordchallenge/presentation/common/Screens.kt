package com.goncalo.swordchallenge.presentation.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
open class BottomBarScreen(val name: String, @Contextual val icon: ImageVector)

@Serializable
data object ScreenCatList : BottomBarScreen("Cat List", Icons.Default.Home)

@Serializable
data object ScreenCatFavourite : BottomBarScreen("Favourite List", Icons.Default.Favorite)

@Serializable
data class CatDetailScreen(val catId: String)

fun getBottomBarScreens() = listOf(ScreenCatList, ScreenCatFavourite)