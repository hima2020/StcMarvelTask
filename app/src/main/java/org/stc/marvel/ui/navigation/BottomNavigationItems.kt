package org.stc.marvel.ui.navigation
import org.stc.marvel.constants.Constants
sealed class NavigationScreens(var screenRoute: String) {
    data object ListScreen : NavigationScreens(Constants.MarvelListRoute)

}
