package org.stc.marvel.ui.list.view

import androidx.annotation.ColorRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import org.stc.marvel.R
import org.stc.marvel.components.CharacterItem
import org.stc.marvel.components.NetworkErrorView
import org.stc.marvel.data.model.CharacterItem
import org.stc.marvel.ui.list.viewmodel.HomeViewModel


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {
    val characters = viewModel.characterList.collectAsLazyPagingItems()

    val backgroundColor = MaterialTheme.colorScheme.background

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .background(backgroundColor)
        ) {
            when {
                characters.loadState.refresh is LoadState.Loading -> {
                    if (viewModel.isFirstLoading) {
                        Box(modifier = Modifier.fillMaxSize().background(color = colorResource(id = R.color.splash_color))) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_splash_screen),
                                contentDescription = "Marvel Logo",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .wrapContentSize(Alignment.Center)
                            )
                        }

                        viewModel.isFirstLoading = false
                    } else {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentSize(Alignment.Center)
                        )
                    }

                }

                characters.loadState.refresh is LoadState.Error -> {
                    val error = characters.loadState.refresh as LoadState.Error
                    var message = error.error.message
                    message = if (message?.contains("Unable to resolve") == true) {
                        stringResource(R.string.network_error_message)
                    } else {
                        stringResource(R.string.something_went_wrong)
                    }
                    NetworkErrorView(
                        message = message,
                        onRetry = { characters.retry() }
                    )
                }

                else -> {
                    MarvelCharacterList(
                        characters = characters,
                        navController = navController
                    )
                }
            }
        }
    }
}


@Composable
fun MarvelCharacterList(
    characters: LazyPagingItems<CharacterItem>,
    navController: NavController
) {
    val backgroundColor = MaterialTheme.colorScheme.background

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
    ) {
        item {
            Header()
        }

        items(characters.itemCount) { index ->
            characters[index]?.let { character ->
                CharacterItem(character = character, navController = navController)
                Spacer(modifier = Modifier.height(2.dp))
            }
        }

        when (characters.loadState.append) {
            is LoadState.Loading -> {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            else -> Unit
        }
    }
}


@Composable
fun Header() {
    val contentColor = MaterialTheme.colorScheme.onBackground

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.marvel_logo),
            contentDescription = "Marvel Logo",
            modifier = Modifier
                .height(50.dp)
                .weight(1f),
            alignment = Alignment.Center
        )
    }
}


