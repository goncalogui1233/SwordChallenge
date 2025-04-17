package com.goncalo.swordchallenge.presentation.catlist.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import com.goncalo.swordchallenge.domain.model.CatInformation
import com.goncalo.swordchallenge.presentation.catlist.viewmodel.CatListViewModel

@Composable
fun CatListScreen(modifier: Modifier = Modifier, viewModel: CatListViewModel) {
    val listItems = viewModel.catList.collectAsLazyPagingItems()

    if(listItems.loadState.refresh is LoadState.Loading) {
        CircularProgressIndicator()
    } else {

        LazyVerticalGrid(columns = GridCells.Fixed(3)) {
            items(count = listItems.itemCount) {
                val catItem = listItems[it]
                catItem?.let { item ->
                    CatListItem(item = item)
                }
            }
        }

        if(listItems.loadState.append is LoadState.Loading) {
            Box (modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter){
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun CatListItem(modifier: Modifier = Modifier, item: CatInformation) {
    Column(
        modifier = modifier.padding(10.dp)
    ) {
        AsyncImage(
            model = item.url,
            contentDescription = null,
            modifier = Modifier.size(125.dp),
            contentScale = ContentScale.Crop
        )
        Text(text = item.breedName)
    }


}