package org.stc.marvel.ui.details.viewmodel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.stc.marvel.data.model.CharachtersResponse
import org.stc.marvel.data.model.CharacterItem
import org.stc.marvel.data.model.NetworkResult
import org.stc.marvel.data.repo.MarvelRepository
import org.stc.marvel.ui.details.state.ScreenState

import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val marvelRepository: MarvelRepository
) : ViewModel() {

     var selectedId = 0

    // States for different Marvel data
    var stateComicsMarvel by mutableStateOf(ScreenState())
    var stateSeriesMarvel by mutableStateOf(ScreenState())
    var stateEventsMarvel by mutableStateOf(ScreenState())
    var stateStoriesMarvel by mutableStateOf(ScreenState())

    // Function to handle the flow and fetch data
    private suspend fun fetchData(
        fetchFunction: suspend () -> Flow<NetworkResult<CharachtersResponse>>,
    ): List<CharacterItem> {
        return try {
            val result = fetchFunction().first()

            when (result) {
                is NetworkResult.Success -> {
                    result.data?.data?.characterItems ?: emptyList()
                }
                is NetworkResult.Error -> {
                    stateComicsMarvel = stateComicsMarvel.copy(error = result.message)
                    emptyList()
                }
                is NetworkResult.Loading -> {
                    emptyList()
                }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Load Comics
    fun loadComics() {
        if (stateComicsMarvel.items.isEmpty() && stateComicsMarvel.isLoading.not()) {
            viewModelScope.launch {
                stateComicsMarvel = stateComicsMarvel.copy(isLoading = true)
                val comics = fetchData({ marvelRepository.fetchComics( selectedId) })
                stateComicsMarvel = stateComicsMarvel.copy(
                    items = comics,
                    isLoading = false,
                    error = if (comics.isEmpty()) "Failed to load comics" else null
                )
            }
        }
    }

    // Load Series
    fun loadSeries() {
        if (stateSeriesMarvel.items.isEmpty() && stateSeriesMarvel.isLoading.not()) {
            viewModelScope.launch {
                stateSeriesMarvel = stateSeriesMarvel.copy(isLoading = true)
                val series = fetchData({ marvelRepository.fetchSeries(selectedId) })
                stateSeriesMarvel = stateSeriesMarvel.copy(
                    items = series,
                    isLoading = false,
                    error = if (series.isEmpty()) "Failed to load series" else null
                )
            }
        }
    }

    // Load Events
    fun loadEvents() {
        if (stateEventsMarvel.items.isEmpty() && stateEventsMarvel.isLoading.not()) {
            viewModelScope.launch {
                stateEventsMarvel = stateEventsMarvel.copy(isLoading = true)
                val events = fetchData({ marvelRepository.fetchEvents( selectedId) })
                stateEventsMarvel = stateEventsMarvel.copy(
                    items = events,
                    isLoading = false,
                    error = if (events.isEmpty()) "Failed to load events" else null
                )
            }
        }
    }

    // Load Stories
    fun loadStories() {
        if (stateStoriesMarvel.items.isEmpty() && stateStoriesMarvel.isLoading.not()) {
            viewModelScope.launch {
                stateStoriesMarvel = stateStoriesMarvel.copy(isLoading = true)
                val stories = fetchData({ marvelRepository.fetchStories(selectedId) })
                stateStoriesMarvel = stateStoriesMarvel.copy(
                    items = stories,
                    isLoading = false,
                    error = if (stories.isEmpty()) "Failed to load stories" else null
                )
            }
        }
    }
    fun loadChachterData(charId:Int){
        selectedId = charId
        loadComics()
        loadSeries()
        loadEvents()
        loadStories()
    }

    // Initialize data loading
    init {

    }
}