package com.diegulog.marvel.data

import androidx.paging.PagingSource
import com.diegulog.marvel.api.MarvelService

private const val OFFSET_INDEX = 0

class CharactersPagingSource(private val service: MarvelService) : PagingSource<Int, CharacterResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterResponse> {
        val offset = params.key ?: OFFSET_INDEX
        return try {
            val response = service.getCharacters(limit = params.loadSize, offset = offset )
            val result = response.data.results
            LoadResult.Page(
                data = result,
                prevKey = null ,
                nextKey = if (offset >= response.data.total) null else offset + params.loadSize
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}
