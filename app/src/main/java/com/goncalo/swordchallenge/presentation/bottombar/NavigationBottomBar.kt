package com.goncalo.swordchallenge.presentation.bottombar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.goncalo.swordchallenge.presentation.common.helpers.ScreenCatFavourite
import com.goncalo.swordchallenge.presentation.common.helpers.ScreenCatList
import com.goncalo.swordchallenge.presentation.common.helpers.getBottomBarScreens

@Composable
fun BottomNavigationBarUI(modifier: Modifier = Modifier, navController: NavHostController) {

    val bottomBarList = getBottomBarScreens()

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    //Get the screen class associated with the current route class
    val screenClass = when(currentRoute) {
        ScreenCatList::class.qualifiedName -> ScreenCatList
        ScreenCatFavourite::class.qualifiedName -> ScreenCatFavourite
        else -> null
    }

    //If not null, screen has the data needed to display the bottom bar
    if(screenClass != null) {
        NavigationBar {
            bottomBarList.forEach { screen ->
                NavigationBarItem(
                    selected = screen == screenClass,
                    onClick = { navController.navigate(screen) },
                    icon = {
                        Icon(
                            imageVector = screen.icon,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(
                            text = screen.name,
                            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium)
                        )
                    }
                )
            }
        }
    }
}