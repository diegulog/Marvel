package com.diegulog.marvel.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.diegulog.marvel.api.MarvelService
import com.diegulog.marvel.data.CharacterResponse
import com.diegulog.marvel.data.DetailsRepository
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: DetailsRepository by lazy {
        DetailsRepository(MarvelService.getInstance())
    }

    private var resultMutableLiveData = MutableLiveData<CharacterResponse?>()
    val result: LiveData<CharacterResponse?>
        get() = resultMutableLiveData


    fun getCharacterById(id: Int) = viewModelScope.launch {
        resultMutableLiveData.value = repository.getCharacterById(id)
    }


}