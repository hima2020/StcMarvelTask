package org.stc.marvel.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.google.gson.Gson
import org.stc.marvel.R
import org.stc.marvel.custom.CustomTextDrawer
import org.stc.marvel.data.model.CharacterItem
import org.stc.marvel.ui.navigation.NavigationScreens
import org.stc.marvel.ui.theme.Roboto

@Composable
fun CharacterItem(
    character: CharacterItem,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 3.dp)
            .clickable {
                navController.navigate(
                    NavigationScreens.Details.screenRoute + "?character=${
                        Gson().toJson(character)
                    }"
                )
            }
    ) {
        val painter = rememberAsyncImagePainter(
            model = "${character.thumbnail?.path}.${character.thumbnail?.extension}",
            placeholder = painterResource(id = R.drawable.ic_gallery),
            error = painterResource(id = R.drawable.ic_gallery)
        )

        Image(
            painter = painter,
            contentDescription = character.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(y = (-20).dp)
                .clip(CustomTextDrawer(slantAngle = 15f))
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = character.name ?: "",
                color = colorResource(id = R.color.black),
                fontFamily = Roboto,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}


