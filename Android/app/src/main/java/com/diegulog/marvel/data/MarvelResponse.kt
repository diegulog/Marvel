package com.diegulog.marvel.data

data class MarvelResponse(
    val code: Int,
    val data: Data
)

data class Data (
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<CharacterResponse>
)

data class CharacterResponse (
    val id: Int,
    val name: String,
    val description: String,
    val modified: String,
    val thumbnail: Thumbnail,
    val resourceURI: String,
){
    fun thumbnailUrl(imageVariant: String): String{
        val url =  "${thumbnail.path}/$imageVariant.${thumbnail.extension}"
        return url.replace("http", "https")
    }
}

data class Thumbnail (
    val path: String,
    val extension: String
)
