package org.stc.marvel.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import org.stc.marvel.R

@Composable
fun ComicImageScreen(imageUrls: List<String>, navController: NavController) {

    Surface(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = rememberAsyncImagePainter(model = imageUrls.first()),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.3f)
                .blur(radius = 20.dp, edgeTreatment = BlurredEdgeTreatment.Rectangle)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(0.8f)
                .background(color = Color.Black)
                .blur(radius = 10.dp)
        )
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp, horizontal = 20.dp)


        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_back),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.background),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(30.dp)
                    .padding(2.dp).clickable {
                        navController.navigateUp()
                    }

            )

            HorizontalImageGallery(imageUrls = imageUrls)
        }
    }

}