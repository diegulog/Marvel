package com.diegulog.marvel.data
import com.diegulog.marvel.api.MarvelService
import java.lang.Exception

class DetailsRepository(private val service: MarvelService) {

    suspend fun getCharacterById(id: Int): CharacterResponse? {
          return try {
              service.getCharacterById(id).data.results.firstOrNull()
          }catch (e:Exception){
              null
          }
    }

}