package com.app.omdbassignment.ui.movielist.datasource

import MovieSearchResponse
import android.util.Log
import com.app.omdbassignment.networkapiclient.IAPICallback
import com.app.omdbassignment.ui.moviesearch.MovieSearchAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection



class MovieSearchRepositoryImpl(
    val movieSearchAPI: MovieSearchAPI
) : IMovieSearchRepository {

    override fun callMovieSearchAPI(
        movieName: String, apiKey: String,
        movieSearchListener: IAPICallback<MovieSearchResponse, String>
    ) {

        val movieSearchCall: Call<MovieSearchResponse> =
            movieSearchAPI.getMovieList(apiKey, movieName)

        movieSearchCall?.enqueue(object : Callback<MovieSearchResponse> {

            override fun onFailure(call: Call<MovieSearchResponse>, t: Throwable) {
                movieSearchListener.onResponseFailure("Try Again")
                Log.e("out", "failure")
            }

            override fun onResponse(
                call: Call<MovieSearchResponse>,
                response: Response<MovieSearchResponse>
            ) {
                if (response.isSuccessful && response.code() == HttpURLConnection.HTTP_OK) {

                    if (response.body() != null) {
                        val movieSearchResponse = response.body()!!
                        if (movieSearchResponse.totalResults > 0) {
                            movieSearchListener.onResponseSuccess(movieSearchResponse)
                            Log.e("out", "success 200")
                        } else {
                            movieSearchListener.onResponseFailure("Movie not found!")
                        }

                    }


                } else {
                    movieSearchListener.onResponseFailure(response.message())
                    Log.e("out", "success not 200")
                }
            }
        })
    }


}

