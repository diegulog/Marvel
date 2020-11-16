package com.diegulog.marvel.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.diegulog.marvel.api.MarvelService
import kotlinx.coroutines.flow.Flow

class CharactersRepository(private val service: MarvelService) {

    fun getCharacters(): Flow<PagingData<CharacterResponse>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = { CharactersPagingSource(service) }
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }
}
