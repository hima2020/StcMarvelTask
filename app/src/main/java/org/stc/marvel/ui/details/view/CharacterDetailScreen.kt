package org.stc.marvel.ui.details.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.google.gson.Gson
import org.stc.marvel.R
import org.stc.marvel.data.model.CharacterItem
import org.stc.marvel.data.model.PreviewModel
import org.stc.marvel.ui.details.state.ScreenState
import org.stc.marvel.ui.details.viewmodel.DetailsViewModel
import org.stc.marvel.ui.navigation.NavigationScreens

@Composable
fun CharacterDetailScreen(
    navController: NavController,
    selectedCharacterItem: CharacterItem?,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    LaunchedEffect(selectedCharacterItem?.id) {
        selectedCharacterItem?.id?.let {
            viewModel.loadChachterData(it)
        }
    }

    val imageUrl =
        "${selectedCharacterItem?.thumbnail?.path}.${selectedCharacterItem?.thumbnail?.extension}"

    Surface(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = rememberAsyncImagePainter(model = imageUrl),
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

        Column {
            Surface(
                modifier = Modifier.height(200.dp)
            ) {
                Box {
                    Image(
                        painter = rememberAsyncImagePainter(model = imageUrl),
                        contentDescription = selectedCharacterItem?.description,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.background),
                        contentScale = ContentScale.Inside,
                        modifier = Modifier
                            .padding(start = 25.dp, top = 40.dp)
                            .size(30.dp)
                            .clickable { navController.navigateUp() }
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp, bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {

                // Name Section
                item {
                    Text("Name", color = Color.Red, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text(
                        text = selectedCharacterItem?.name ?: "",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Description Section
                if (!selectedCharacterItem?.description.isNullOrEmpty()) {
                    item {
                        Text(
                            "Description",
                            color = Color.Red,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = selectedCharacterItem?.description ?: "",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Comics Section
                item {
                    sectionTitle("Comics")
                }
                item {
                    marvelSection(
                        navController,
                        state = viewModel.stateComicsMarvel,
                        onRetry = { viewModel.loadComics() }
                    )
                }

                item { sectionTitle("Series") }
                item {
                    marvelSection(
                        navController,
                        state = viewModel.stateSeriesMarvel,
                        onRetry = { viewModel.loadSeries() }
                    )
                }
                item { sectionTitle("Events") }
                item {
                    marvelSection(navController,
                        state = viewModel.stateEventsMarvel,
                        onRetry = { viewModel.loadEvents() }
                    )
                }
                item { sectionTitle("Stories") }
                item {
                    marvelSection(navController,
                        state = viewModel.stateStoriesMarvel,
                        onRetry = { viewModel.loadStories() }
                    )
                }
                item { RelatedLinksSection() }
            }
        }
    }
}

@Composable
fun sectionTitle(title: String) {
    Text(
        text = title,
        color = Color.Red,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 16.dp)
    )
}

@Composable
fun marvelSection(
    navController: NavController,
    state: ScreenState,
    onRetry: () -> Unit
) {
    when {
        state.isLoading -> {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        state.error != null -> {
            RetrySection(
                errorMessage = state.error ?: "Unknown error",
                onRetry = onRetry
            )
        }

        state.items.isEmpty() -> {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No data available.",
                    color = MaterialTheme.colorScheme.background,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        else -> {
            LazyRow {
                items(state.items) { item ->
                    if (item.thumbnail != null)
                        Image(
                            painter = rememberAsyncImagePainter("${item.thumbnail.path}.${item.thumbnail.extension}"),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(100.dp)
                                .padding(2.dp)
                                .clickable {
                                    val images: ArrayList<String> = ArrayList()
                                    for (image in state.items) {
                                        images.add("${image.thumbnail?.path}.${image.thumbnail?.extension}")
                                    }
                                    Log.d("IMAGESUSERPREVIEW",images.toString())
                                    navController.navigate(
                                        NavigationScreens.Preview.screenRoute + "?preview=${
                                            Gson().toJson(PreviewModel(images.toList()))
                                        }"
                                    )

                                }
                        )
                    else Image(
                        painter = painterResource(id = R.drawable.ic_place_holder),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.background),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .padding(2.dp)

                    )
                }
            }
        }
    }
}

@Composable
fun RetrySection(
    errorMessage: String,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.background,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onRetry) {
                Text(text = "Retry")
            }
        }
    }
}

@Composable
fun RelatedLinksSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "RELATED LINKS",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(8.dp))

        RelatedLinkItem(text = "Detail")
        RelatedLinkItem(text = "Wiki")
        RelatedLinkItem(text = "Comiclink")
    }
}

@Composable
fun RelatedLinkItem(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp, bottom = 15.dp, end = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            color = Color.White
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_forward), // Replace with your arrow icon
            contentDescription = "Arrow",
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
    }
}