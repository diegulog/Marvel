package com.diegulog.marvel.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.diegulog.marvel.api.MarvelService
import com.diegulog.marvel.data.CharacterResponse
import com.diegulog.marvel.data.CharactersRepository
import kotlinx.coroutines.flow.Flow

class CharactersViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CharactersRepository by lazy {
        CharactersRepository(MarvelService.getInstance())
    }

    var currentResult: Flow<PagingData<CharacterResponse>>

    init {
        currentResult = repository.getCharacters().cachedIn(viewModelScope)
    }


}