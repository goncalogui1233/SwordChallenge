package com.goncalo.swordchallenge.presentation.catdetail.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.goncalo.swordchallenge.presentation.common.views.ShimmerEffect

@Composable
fun CatDetailLoading(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        ShimmerEffect(
            modifier = Modifier
                .fillMaxWidth()
                .size(325.dp)
                .padding(bottom = 16.dp)
                .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
        )
        ShimmerEffect(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .padding(start = 8.dp, bottom = 16.dp, end = 8.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        ShimmerEffect(
            modifier = Modifier
                .fillMaxWidth()
                .height(128.dp)
                .padding(start = 8.dp, bottom = 16.dp, end = 8.dp)
                .clip(RoundedCornerShape(12.dp))
        )
    }
}