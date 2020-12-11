package com.diegulog.marvel.data

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getCharacters(): Flow<PagingData<CharacterResponse>>
    suspend fun getCharacterById(id: Int): CharacterResponse?

}