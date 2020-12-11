package com.diegulog.marvel.api

import com.diegulog.marvel.data.MarvelResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.security.MessageDigest

interface MarvelService {
    @GET("characters")
    suspend fun getCharacters(
        @Query("orderBy") orderBy: String = "name",
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int
    ): MarvelResponse

    @GET("characters/{characterId}")
    suspend fun getCharacterById(
        @Path("characterId") characterId: Int
    ): MarvelResponse
}