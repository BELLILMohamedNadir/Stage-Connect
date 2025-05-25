package com.example.stageconnect.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.stageconnect.R

@Composable
fun ProfileImage(
    imageUri: String?,
    imageSize: Dp = 120.dp,
    circleShape: Boolean = true
) {
    val context = LocalContext.current
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data(imageUri)
            .crossfade(true)
            .size(coil.size.Size.ORIGINAL)
            .build(),
        placeholder = painterResource(R.drawable.ic_profile),
        error = painterResource(R.drawable.ic_profile)
    )

    val state = painter.state

    Box(
        modifier = Modifier
            .size(imageSize)
            .then(
                if (circleShape)
                    Modifier.clip(CircleShape)
                else
                    Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter,
            contentDescription = "ic_profile",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        if (state is AsyncImagePainter.State.Loading) {
            CircularProgressIndicator(modifier = Modifier.size(32.dp))
        }
    }
}
