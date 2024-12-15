package org.stc.marvel.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.stc.marvel.data.model.CharacterItem
import org.stc.marvel.domain.api.ApiService

class MarvelPagingSource(
    private val apiService: ApiService
) : PagingSource<Int, CharacterItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterItem> {
        val position = params.key ?: 0 // Start with offset 0
        return try {
            val response =
                apiService.getMarvelCharacters(offset = position, limit = params.loadSize)
            val characterItems = response.body()?.data?.characterItems ?: emptyList()

            LoadResult.Page(
                data = characterItems,
                prevKey = if (position == 0) null else position - params.loadSize,
                nextKey = if (characterItems.isEmpty()) null else position + params.loadSize
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CharacterItem>): Int? {
        return state.anchorPosition?.let { state.closestPageToPosition(it)?.prevKey }
    }
}
