package org.stc.marvel.domain.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.stc.marvel.data.model.CharachtersResponse
import org.stc.marvel.data.model.CharacterItem
import org.stc.marvel.data.model.NetworkResult
import org.stc.marvel.data.repo.MarvelRepository
import org.stc.marvel.domain.api.ApiService
import org.stc.marvel.ui.paging.MarvelPagingSource

class MarvelsRepositoryImpl(
    private val apiSefvice: ApiService,
) : MarvelRepository {
    override suspend fun searchCharacters(
        limit: Int?,
        offset: Int
    ): Flow<PagingData<CharacterItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10, // Optimize page size for your API response
                prefetchDistance = 5, // Preload items to improve UX
                enablePlaceholders = false // Disable placeholders to show a blank space instead
            ),
            pagingSourceFactory = { MarvelPagingSource(apiSefvice) }
        ).flow
    }

    override suspend fun fetchComics(
        charId: Int
    ) = flow<NetworkResult<CharachtersResponse>> {
        // emit(NetworkResult.Loading())
        with(apiSefvice.getMarvelCharacterComics(charId)) {
            if (isSuccessful) {
                emit(NetworkResult.Success(this.body()))
            } else {
                emit(NetworkResult.Error(this.errorBody().toString()))
            }
        }
    }.catch {
        emit(NetworkResult.Error(it.localizedMessage))
    }

    override suspend fun fetchEvents(
        charId: Int
    ) = flow<NetworkResult<CharachtersResponse>> {
        // emit(NetworkResult.Loading())
        with(apiSefvice.getMarvelCharacterEvents(charId)) {
            if (isSuccessful) {
                emit(NetworkResult.Success(this.body()))
            } else {
                emit(NetworkResult.Error(this.errorBody().toString()))
            }
        }
    }.catch {
        emit(NetworkResult.Error(it.localizedMessage))
    }

    override suspend fun fetchSeries(
        charId: Int
    ) = flow<NetworkResult<CharachtersResponse>> {
        // emit(NetworkResult.Loading())
        with(apiSefvice.getMarvelCharacterSeries(charId)) {
            if (isSuccessful) {
                emit(NetworkResult.Success(this.body()))
            } else {
                emit(NetworkResult.Error(this.errorBody().toString()))
            }
        }
    }.catch {
        emit(NetworkResult.Error(it.localizedMessage))
    }

    override suspend fun fetchStories(
        charId: Int
    ) = flow<NetworkResult<CharachtersResponse>> {
        // emit(NetworkResult.Loading())
        with(apiSefvice.getMarvelCharacterStories(charId)) {
            if (isSuccessful) {
                emit(NetworkResult.Success(this.body()))
            } else {
                emit(NetworkResult.Error(this.errorBody().toString()))
            }
        }
    }.catch {
        emit(NetworkResult.Error(it.localizedMessage))
    }
}

