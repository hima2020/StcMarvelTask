package org.stc.marvel.ui.details.state

import org.stc.marvel.data.model.CharacterItem

data class ScreenState(
    val isLoading: Boolean = false,
    val items: List<CharacterItem> = emptyList(),
    val error: String? = null,
    val endReached: Boolean = false,
    val page: Int = 0
)