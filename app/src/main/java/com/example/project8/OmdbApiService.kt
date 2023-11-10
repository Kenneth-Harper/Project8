package com.example.project8

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApiService
{
    @GET(".")
    suspend fun getMovieDetails(
        @Query("s") title: String,
        @Query("apikey") apiKey: String
    ): Response<MovieApiResponse>
}