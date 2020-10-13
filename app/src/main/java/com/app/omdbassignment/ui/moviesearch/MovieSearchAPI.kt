package com.app.omdbassignment.ui.moviesearch

import MovieSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieSearchAPI {

    @GET(".")
    fun getMovieList(
        @Query("apikey") api_key: String,
        @Query("s") name: String
    ): retrofit2.Call<MovieSearchResponse>

}