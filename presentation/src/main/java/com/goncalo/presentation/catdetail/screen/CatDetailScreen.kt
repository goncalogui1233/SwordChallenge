package com.goncalo.presentation.catdetail.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.goncalo.domain.model.classes.CatInformation
import com.goncalo.domain.model.enums.CatDetailRequestSource
import com.goncalo.presentation.R
import com.goncalo.presentation.catdetail.viewmodel.CatDetailViewModel
import com.goncalo.presentation.catdetail.views.CatDetailLoading
import com.goncalo.presentation.common.views.CatErrorMessage
import com.goncalo.presentation.common.helpers.UIState

@Composable
fun CatDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: CatDetailViewModel,
    catId: String,
    catDetailRequestSource: String,
    navController: NavController
) {

    LaunchedEffect(Unit) {
        viewModel.getCatDetails(catId, CatDetailRequestSource.valueOf(catDetailRequestSource))
    }

    val catDetailState = viewModel.uiState.collectAsStateWithLifecycle().value

    //Header Bar with back button
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(56.dp)) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
        }
    }

    when(catDetailState) {
        is UIState.Loading -> {
            CatDetailLoading(modifier = Modifier.padding(top = 56.dp))
        }

        is UIState.Success -> {
            catDetailState.data?.let { details ->
                CatDetails(details = details, modifier = Modifier.padding(top = 56.dp)) {
                    viewModel.updateCatFavourite(details)
                }
            }
        }

        is UIState.Error -> {
            CatErrorMessage(errorMessage = catDetailState.message, modifier = Modifier
                .fillMaxSize()
                .padding(top = 56.dp))
        }
    }
}

@Composable
fun CatDetails(
    modifier: Modifier = Modifier,
    details: CatInformation,
    onFavouriteClick: () -> Unit
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = details.url,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.question_mark),
            modifier = Modifier
                .fillMaxWidth()
                .height(325.dp)
                .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
        )

        CatDetailNameAndFavorite(details = details) {
            onFavouriteClick()
        }

        details.description?.let { description ->
            Text(
                text = description,
                style = TextStyle(
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, top = 16.dp, end = 8.dp)
            )
        }

        details.origin?.let {
            Text(
                text = "Origin: ${details.origin}",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, top = 16.dp, end = 8.dp)
            )
        }


        details.temperament?.let { temperament ->
            CatDetailsTemperament(
                temperament = temperament,
                modifier = Modifier.padding(start = 8.dp, top = 16.dp, end = 8.dp)
            )
        }

    }
}

@Composable
fun CatDetailNameAndFavorite(
    modifier: Modifier = Modifier,
    details: CatInformation,
    onFavouriteClick: () -> Unit
) {
    details.breedName?.let { breedName ->
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 16.dp, end = 8.dp)
        ) {

            Text(
                text = breedName,
                style = TextStyle(
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                ),
            )


            val starIcon =
                if (details.isFavourite) R.drawable.star_filled else R.drawable.star_outline
            Icon(
                painter = painterResource(id = starIcon),
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        onFavouriteClick()
                    }
            )
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CatDetailsTemperament(modifier: Modifier = Modifier, temperament: String) {
    Column(modifier = modifier) {
        Text(text = "Cat Temperament", style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold))

        val temperamentList = temperament.split(",")
        FlowRow(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)) {
            temperamentList.forEach { temperament ->
                Card(modifier = Modifier.padding(4.dp)) {
                    Text(text = temperament.trim(), modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                }
            }
        }
    }
}