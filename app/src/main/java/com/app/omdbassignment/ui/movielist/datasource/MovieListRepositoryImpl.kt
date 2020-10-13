package com.app.omdbassignment.ui.movielist.datasource

import MovieDetailResponse
import MovieSearchResponse
import android.util.Log
import com.app.omdbassignment.networkapiclient.IAPICallback
import com.app.omdbassignment.ui.moviedetail.MovieDetailAPI
import com.app.omdbassignment.ui.moviesearch.MovieSearchAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection


class MovieListRepositoryImpl(
    val movieDetailAPI: MovieDetailAPI
) : IMovieListRepository {

    override fun callMovieDetailAPI(
        movieName: String, apiKey: String,
        movieDetailListener: IAPICallback<MovieDetailResponse, String>
    ) {

        val movieSearchCall: Call<MovieDetailResponse> =
            movieDetailAPI.getMovieDetail(apiKey, movieName)

        movieSearchCall?.enqueue(object : Callback<MovieDetailResponse> {

            override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                movieDetailListener.onResponseFailure("Try Again")
                Log.e("out", "failure")
            }

            override fun onResponse(
                call: Call<MovieDetailResponse>,
                response: Response<MovieDetailResponse>
            ) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    val movieDetailResponse: MovieDetailResponse = response.body()!!
                    movieDetailListener.onResponseSuccess(movieDetailResponse)
                    Log.e("out", "success 200")
                } else {
                    movieDetailListener.onResponseFailure(response.message())
                    Log.e("out", "success not 200")
                }
            }
        })
    }


}

