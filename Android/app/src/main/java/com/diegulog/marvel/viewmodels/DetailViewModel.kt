package com.diegulog.marvel.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diegulog.marvel.data.CharacterResponse
import com.diegulog.marvel.data.NetworkRepository
import kotlinx.coroutines.launch

class DetailViewModel @ViewModelInject constructor(private val repository: NetworkRepository)  : ViewModel() {

    private var resultMutableLiveData = MutableLiveData<CharacterResponse?>()
    val result: LiveData<CharacterResponse?>
        get() = resultMutableLiveData

    fun getCharacterById(id: Int) = viewModelScope.launch {
        resultMutableLiveData.value = repository.getCharacterById(id)
    }

}