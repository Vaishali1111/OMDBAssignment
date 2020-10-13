package com.app.omdbassignment.ui.movielist.datasource

import MovieSearchResponse
import com.app.omdbassignment.networkapiclient.IAPICallback

interface IMovieSearchRepository {
    fun callMovieSearchAPI(
        movieName: String, apiKey: String,
        loginResponseListener: IAPICallback<MovieSearchResponse, String>
    )
}