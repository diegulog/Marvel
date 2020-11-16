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

    companion object {
        private const val BASE_URL = "https://gateway.marvel.com/v1/public/"
        private const val publicKey = "d86e70f5110515da2b5ea7f48d347f58"
        private const val privateKey = "1287b638d82c4ad5409077e5eff5333d67d97424"
        private const val ts = "diegulog"
        private val md5: String by lazy {
            val bytes = MessageDigest.getInstance("MD5").digest("$ts$privateKey$publicKey".toByteArray())
            bytes.joinToString("") {
                "%02x".format(it)
            }
        }

        private var INSTANCE: MarvelService? = null

        fun getInstance(): MarvelService =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: create().also {
                    INSTANCE = it
                }
            }


        private fun create(): MarvelService {
            val logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }
            val client = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val newRequest = chain.request()
                    val newRequestHttpUrl = newRequest.url
                    val url = newRequestHttpUrl.newBuilder()
                        .addQueryParameter("apikey", publicKey)
                        .addQueryParameter("ts", ts)
                        .addQueryParameter("hash", md5)
                        .build()
                    chain.proceed(newRequest.newBuilder().url(url).build())
                }
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MarvelService::class.java)
        }
    }
}