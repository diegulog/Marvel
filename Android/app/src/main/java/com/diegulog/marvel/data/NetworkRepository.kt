package com.diegulog.marvel.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.diegulog.marvel.api.MarvelService
import kotlinx.coroutines.flow.Flow
import java.lang.Exception
import javax.inject.Inject

class NetworkRepository @Inject constructor(private val service: MarvelService): Repository {

    private val pageSize = 20

    override fun getCharacters(): Flow<PagingData<CharacterResponse>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = pageSize),
            pagingSourceFactory = { CharactersPagingSource(service) }
        ).flow
    }

    override suspend fun getCharacterById(id: Int): CharacterResponse? {
        return try {
            service.getCharacterById(id).data.results.firstOrNull()
        }catch (e: Exception){
            null
        }
    }

}
