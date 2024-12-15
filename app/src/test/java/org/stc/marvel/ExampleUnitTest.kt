package org.stc.marvel

import androidx.paging.PagingData
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.stc.marvel.data.model.CharacterItem

import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.stc.marvel.data.repo.MarvelRepository

class ExampleUnitTest {
    @Test
    fun `stubbing a method returns expected value`() = runTest {
        val mockRepository: MarvelRepository = mock()

        whenever(mockRepository.searchCharacters(10, 0)).thenReturn(flowOf(PagingData.empty()))

        val result = mockRepository.searchCharacters(10, 0)

        assertEquals(flowOf<PagingData<CharacterItem>>(PagingData.empty()), result)

    }
}