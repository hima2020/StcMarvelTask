package org.stc.marvel.domain.api

import org.stc.marvel.data.model.CharachtersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("characters")
    suspend fun getMarvelCharacters(
        @Query("limit") limit: Int?,
        @Query("offset") offset: Int?,

    ): Response<CharachtersResponse>

    @GET("characters/{Id}/comics")
    suspend fun getMarvelCharacterComics(
        @Path("Id") characterId: Int?,
    ): Response<CharachtersResponse>


    @GET("characters/{Id}/events")
    suspend fun getMarvelCharacterEvents(
        @Path("Id") characterId: Int?,
    ): Response<CharachtersResponse>


    @GET("characters/{Id}/series")
    suspend fun getMarvelCharacterSeries(
        @Path("Id") characterId: Int?,
    ): Response<CharachtersResponse>

    @GET("characters/{Id}/stories")
    suspend fun getMarvelCharacterStories(
        @Path("Id") characterId: Int?,
    ): Response<CharachtersResponse>
}