package org.stc.marvel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import org.stc.marvel.ui.list.view.HomeScreen
import org.stc.marvel.ui.navigation.NavigationScreens
import org.stc.marvel.ui.theme.MarvelTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MarvelTheme(darkTheme = isSystemInDarkTheme()) {
                Surface {
                    val textState = remember {
                        mutableStateOf(TextFieldValue(""))
                    }
                    val word = textState.value.text.ifEmpty { null }
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Column {
                            NavigationStack()
                        }

                    }
                }

            }

        }
    }
}


@Composable
fun NavigationStack() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavigationScreens.ListScreen.screenRoute
    ) {
        composable(route = NavigationScreens.ListScreen.screenRoute) {
            HomeScreen(navController = navController)
        }
    }
}