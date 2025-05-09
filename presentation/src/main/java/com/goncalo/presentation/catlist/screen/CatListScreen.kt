package com.goncalo.presentation.catlist.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.goncalo.domain.model.classes.CatInformation
import com.goncalo.domain.model.enums.CatDetailRequestSource
import com.goncalo.presentation.common.views.CatErrorMessage
import com.goncalo.presentation.catlist.viewmodel.CatListViewModel
import com.goncalo.presentation.catlist.views.CatListItem
import com.goncalo.presentation.common.helpers.CatDetailScreen
import com.goncalo.presentation.common.helpers.UIState
import com.goncalo.presentation.common.views.ShimmerEffect

@Composable
fun CatListScreen(
    modifier: Modifier = Modifier,
    viewModel: CatListViewModel,
    navController: NavController
) {
    val listItems = viewModel.catList.collectAsLazyPagingItems()
    val searchItemsUiState = viewModel.catSearchListUiState.collectAsStateWithLifecycle().value

    var isSearching by remember {
        mutableStateOf(false)
    }

    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = "Cats List",
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 16.dp, start = 16.dp)
        )

        CatListSearchBox { breedSearch ->
            isSearching = breedSearch.isNotEmpty()
            viewModel.setBreedSearchName(breedSearch)
        }

        if(isSearching) {
            CatSearchList(uiState = searchItemsUiState, onFavouriteClick = { it -> viewModel.changeCatFavouriteStatus(it)}) {
                navController.navigate(CatDetailScreen(it, CatDetailRequestSource.BREED_LIST.name))
            }
        } else {
            CatCompleteList(listItems = listItems, onFavouriteClick = { it -> viewModel.changeCatFavouriteStatus(it)}) {
                navController.navigate(CatDetailScreen(it, CatDetailRequestSource.BREED_LIST.name))
            }
        }


    }
}

@Composable
fun CatSearchList(
    modifier: Modifier = Modifier,
    uiState: UIState<List<CatInformation>>,
    onFavouriteClick: (CatInformation) -> Unit,
    onItemClicked: (String) -> Unit
) {

    LazyVerticalGrid(columns = GridCells.Fixed(3), modifier = Modifier.fillMaxSize()) {

        when (uiState) {
            is UIState.Loading -> {
                items(15) {
                    ShimmerEffect(
                        modifier = Modifier
                            .width(150.dp)
                            .height(150.dp)
                            .padding(10.dp)
                            .clip(
                                RoundedCornerShape(12.dp)
                            )
                    )
                }
            }

            is UIState.Success -> {

                if(uiState.data.isNullOrEmpty()) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        CatErrorMessage(errorMessage = "No items found to fill the Cat List")
                    }
                } else {
                    val list = uiState.data
                    items(list.size) { index ->
                        val item = list[index]
                        CatListItem(
                            modifier = Modifier.padding(10.dp),
                            item = item,
                            onFavouriteClick = {
                                onFavouriteClick(item)
                            }) {
                            onItemClicked(item.id)
                        }
                    }

                }
            }

            is UIState.Error -> {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    CatErrorMessage(errorMessage = uiState.message)
                }

            }
        }
    }
}

@Composable
fun CatCompleteList(modifier: Modifier = Modifier, listItems: LazyPagingItems<CatInformation>, onFavouriteClick: (CatInformation) -> Unit, onItemClicked: (String) -> Unit) {
    LazyVerticalGrid(columns = GridCells.Fixed(3), modifier = modifier.fillMaxSize()) {
        if (listItems.loadState.refresh is LoadState.Loading) {
            //Loading
            items(15) {
                ShimmerEffect(
                    modifier = Modifier
                        .width(150.dp)
                        .height(150.dp)
                        .padding(10.dp)
                        .clip(
                            RoundedCornerShape(12.dp)
                        )
                )
            }
        } else {
            if (listItems.itemCount == 0) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    CatErrorMessage(errorMessage = "No items found to fill the Cat List")
                }
            } else {
                items(count = listItems.itemCount) {
                    val catItem = listItems[it]
                    catItem?.let { item ->
                        CatListItem(
                            modifier = Modifier.padding(10.dp),
                            item = item,
                            onFavouriteClick = {
                                onFavouriteClick(item)
                            }) {
                            onItemClicked(item.id)
                        }
                    }
                }

                if (listItems.loadState.append is LoadState.Loading) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun CatListSearchBox(modifier: Modifier = Modifier, onTextChange: (String) -> Unit) {
    var searchText by remember {
        mutableStateOf("")
    }

    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = searchText,
        placeholder = {
            Text(text = "Search your favourite cat breed")
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        },
        trailingIcon = {
            if (searchText.isNotEmpty()) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        searchText = ""
                        onTextChange(searchText)
                        focusManager.clearFocus()
                    })
            }
        },
        onValueChange = { searchText = it; onTextChange(searchText) },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 16.dp)
    )
}