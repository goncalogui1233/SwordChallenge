package com.goncalo.presentation.catlist.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.goncalo.domain.model.classes.CatInformation
import com.goncalo.domain.model.enums.CatDetailRequestSource
import com.goncalo.presentation.common.views.CatErrorMessage
import com.goncalo.presentation.catlist.viewmodel.CatListViewModel
import com.goncalo.presentation.catlist.views.CatListItem
import com.goncalo.presentation.common.helpers.CatDetailScreen

@Composable
fun CatFavouriteScreen(modifier: Modifier = Modifier, viewModel: CatListViewModel, navController: NavController) {
    val favoriteItems =
        viewModel.catFavouriteList.collectAsStateWithLifecycle(
            initialValue = listOf()
        )

    if (favoriteItems.value.isNotEmpty()) {
        FavouriteList(
            favouriteItems = favoriteItems.value,
            onFavouriteClick = { viewModel.changeCatFavouriteStatus(it) }) { catId ->
            navController.navigate(CatDetailScreen(catId, CatDetailRequestSource.FAVOURITE_LIST.name))
        }
    } else {
        CatErrorMessage(
            errorMessage = "No Favourite Cats. Go back to the list and add cats that you like.",
            modifier = Modifier.padding(top = 32.dp)
        )
    }
}

@Composable
private fun FavouriteList(
    modifier: Modifier = Modifier,
    favouriteItems: List<CatInformation>,
    onFavouriteClick: (CatInformation) -> Unit,
    onItemClicked: (String) -> Unit
) {
    LazyVerticalGrid(modifier = modifier, columns = GridCells.Fixed(3)) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Text(
                text = "Favourite Cats",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(
                    top = 16.dp,
                    start = 16.dp
                )
            )
        }

        items(favouriteItems) {
            CatFavouriteListItem(catInformation = it, onFavouriteClick = { onFavouriteClick(it) }) {
                onItemClicked(it.id)
            }
        }
    }
}

@Composable
private fun CatFavouriteListItem(
    modifier: Modifier = Modifier,
    catInformation: CatInformation,
    onFavouriteClick: () -> Unit,
    onItemClicked: () -> Unit
) {
    Column(
        modifier = modifier.padding(10.dp)
    ) {
        CatListItem(item = catInformation, onFavouriteClick = {
            onFavouriteClick()
        }) {
            onItemClicked()
        }

        catInformation.lifeSpan?.let { lifeSpan ->
            Text(
                text = "Life Span: ${lifeSpan.split("-").last().trim()}",
                modifier = Modifier.padding(top = 5.dp)
            )
        }
    }
}

@Composable
private fun FavouriteEmptyState(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "No Favourite Cats. Go back to the list and add cats that you like",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(top = 16.dp, start = 16.dp)
        )
    }
}