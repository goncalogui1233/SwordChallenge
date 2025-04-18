package com.goncalo.swordchallenge.presentation.catlist.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.goncalo.swordchallenge.R
import com.goncalo.swordchallenge.domain.model.CatInformation

@Composable
fun CatListItem(modifier: Modifier = Modifier, item: CatInformation, onFavouriteClick: () -> Unit) {
    Box(
        modifier = modifier
    ) {
        Column {
            AsyncImage(
                model = item.url,
                contentDescription = null,
                modifier = Modifier
                    .size(125.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Text(
                text = item.breedName,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth().padding(top = 5.dp)
            )
        }

        Box(modifier = Modifier.matchParentSize(), contentAlignment = Alignment.TopEnd) {
            val star =
                painterResource(id = if (item.isFavourite) R.drawable.star_filled else R.drawable.star_outline)
            Icon(
                painter = star,
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        onFavouriteClick()
                    },
                tint = Color.Yellow)
        }
    }
}