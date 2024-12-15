package org.stc.marvel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.stc.marvel.data.model.CharachtersResponse
import org.stc.marvel.data.model.CharacterItem
import org.stc.marvel.data.model.Data
import org.stc.marvel.data.model.NetworkResult
import org.stc.marvel.data.repo.MarvelRepository
import org.stc.marvel.ui.details.viewmodel.DetailsViewModel
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DetailsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val dispatcherRule = MainCoroutineRule()

    @Mock
    lateinit var marvelRepository: MarvelRepository

    private lateinit var viewModel: DetailsViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = DetailsViewModel(marvelRepository)
    }

    @Test
    fun testLoadComicsSuccessfullyUpdatesState() = runTest {
        val mockComics = listOf(CharacterItem(thumbnail = null, name = "", id = 100,description = "Comic 1"), CharacterItem(thumbnail = null, name = "Ibrahim Hany", id = 100,description = "Comic 2"))
        runTest {
            (marvelRepository.fetchComics(viewModel.selectedId))
        }
        viewModel.loadComics()
        assertEquals(mockComics, viewModel.stateComicsMarvel.items)
        assertTrue { viewModel.stateComicsMarvel.isLoading.not() }
        assertEquals(null, viewModel.stateComicsMarvel.error)
    }

    @Test
    fun testLoadComicsHandlesError() = runTest {
        // Mock the repository to return an error
        Mockito.`when`(marvelRepository.fetchComics(viewModel.selectedId)).thenReturn(
            flow { emit(NetworkResult.Error("Failed to load comics")) }
        )

        // Trigger the loadComics function
        viewModel.loadComics()

        // Verify that the state is updated with the error
        assertEquals(emptyList<CharacterItem>(), viewModel.stateComicsMarvel.items)
        assertTrue { viewModel.stateComicsMarvel.isLoading.not() }
        assertEquals("Failed to load comics", viewModel.stateComicsMarvel.error)
    }

    @Test
    fun testLoadComicsHandlesEmptyResult() = runTest {
        Mockito.`when`(marvelRepository.fetchComics(viewModel.selectedId)).thenReturn(
            flow { emit(NetworkResult.Success(CharachtersResponse(null))) }
        )

        // Trigger the loadComics function
        viewModel.loadComics()

        // Verify that the state reflects the empty result
        assertEquals(emptyList<CharacterItem>(), viewModel.stateComicsMarvel.items)
        assertTrue { viewModel.stateComicsMarvel.isLoading.not() }
        assertEquals("Failed to load comics", viewModel.stateComicsMarvel.error)
    }

    @Test
    fun `test loadChachterData triggers all load functions`() = runTest {
        val mockComics = listOf(CharacterItem(name = "Ibrahim Elgohry Test", id = 10, thumbnail = null, description = "Comic 1"))
        Mockito.`when`(marvelRepository.fetchComics(viewModel.selectedId)).thenReturn(
            flow { emit(NetworkResult.Success(CharachtersResponse(Data(100,10,0,mockComics,100)))) }
        )
        Mockito.`when`(marvelRepository.fetchSeries(viewModel.selectedId)).thenReturn(
            flow { emit(NetworkResult.Success(CharachtersResponse(null))) }
        )
        Mockito.`when`(marvelRepository.fetchEvents(viewModel.selectedId)).thenReturn(
            flow { emit(NetworkResult.Success(CharachtersResponse(null))) }
        )
        Mockito.`when`(marvelRepository.fetchStories(viewModel.selectedId)).thenReturn(
            flow { emit(NetworkResult.Success(CharachtersResponse(null))) }
        )

        viewModel.loadChachterData(1)

        assertEquals(mockComics, viewModel.stateComicsMarvel.items)
        assertTrue { viewModel.stateSeriesMarvel.items.isEmpty() }
        assertTrue { viewModel.stateEventsMarvel.items.isEmpty() }
        assertTrue { viewModel.stateStoriesMarvel.items.isEmpty() }
    }
}
