package com.diegulog.marvel.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.diegulog.marvel.data.CharacterResponse
import com.diegulog.marvel.data.NetworkRepository
import kotlinx.coroutines.flow.Flow

class CharactersViewModel @ViewModelInject constructor(private val repository: NetworkRepository) : ViewModel() {


    var currentResult: Flow<PagingData<CharacterResponse>>

    init {
        currentResult = repository.getCharacters().cachedIn(viewModelScope)
    }


}