package com.app.omdbassignment.ui.movielist.datasource

import MovieDetailResponse
import com.app.omdbassignment.networkapiclient.IAPICallback

interface IMovieListRepository {
    fun callMovieDetailAPI(
        movieName: String, apiKey: String,
        loginResponseListener: IAPICallback<MovieDetailResponse, String>
    )
}