package com.app.omdbassignment.ui.moviedetail

import MovieDetailResponse
import MovieSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface MovieDetailAPI {

    @GET(".")
    fun getMovieDetail(
        @Query("apikey") api_key: String,
        @Query("t") name: String
    ): retrofit2.Call<MovieDetailResponse>

}