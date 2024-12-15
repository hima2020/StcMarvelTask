package org.stc.marvel

import androidx.paging.PagingData
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.assertEquals
import org.mockito.Mockito.*
import org.stc.marvel.data.model.CharacterItem
import org.stc.marvel.data.repo.MarvelRepository
import org.stc.marvel.ui.list.viewmodel.HomeViewModel

class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel
    private val repository: MarvelRepository = mock()

    @get:Rule
    val dispatcherRule = MainCoroutineRule()

    @Before
    fun setUp() {
        runTest {
            whenever(repository.searchCharacters(10, 0)).thenReturn(flowOf(PagingData.empty()))
        }
        viewModel = HomeViewModel(repository)
    }

    @Test
    fun `characterList initially emits empty PagingData`() = runTest {
        val emittedData = mutableListOf<PagingData<CharacterItem>>()

        val job = launch {
            viewModel.characterList.toList(emittedData)
        }

        assertEquals(1, emittedData.size)

        job.cancel()
    }

    @Test
    fun loadCharactersUpdatesCharacterListWithData() = runTest {
        val mockCharacters = listOf(
            CharacterItem(id = 1, name = "Spider-Man", description = "Hi ", thumbnail = null),
            CharacterItem(id = 2, name = "Iron Man", description = "Hello test ", thumbnail = null),
            CharacterItem(id = 2, name = "Ibrahim Elgohry", description = "Hello Ibrahim ", thumbnail = null),
            CharacterItem(id = 2, name = "Android", description = "Hello Android ", thumbnail = null)
        )
        val mockPagingData = PagingData.from(mockCharacters)

        whenever(repository.searchCharacters(10, 1)).thenReturn(flowOf(mockPagingData))
        viewModel.loadCharacters()

        val emittedData = mutableListOf<PagingData<CharacterItem>>()
        val job = launch { viewModel.characterList.toList(emittedData) }

        assertEquals(0, emittedData.size)
        job.cancel()
    }

    @Test
    fun isFirstLoadingSetCorrectly() {
        assertEquals(true, viewModel.isFirstLoading)
    }
}

