package org.stc.marvel.data.repo

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.stc.marvel.data.model.CharachtersResponse
import org.stc.marvel.data.model.CharacterItem
import org.stc.marvel.data.model.NetworkResult

interface MarvelRepository {
    suspend fun searchCharacters(limit: Int? = null,offset:Int): Flow<PagingData<CharacterItem>>
    suspend fun fetchComics(charId: Int): Flow<NetworkResult<CharachtersResponse>>
    suspend fun fetchEvents(charId: Int): Flow<NetworkResult<CharachtersResponse>>
    suspend fun fetchSeries(charId: Int): Flow<NetworkResult<CharachtersResponse>>
    suspend fun fetchStories(charId: Int): Flow<NetworkResult<CharachtersResponse>>
}
