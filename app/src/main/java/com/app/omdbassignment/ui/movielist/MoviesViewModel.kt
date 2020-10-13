package com.app.omdbassignment.ui.movielist

import MovieDetailResponse
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.omdbassignment.networkapiclient.IAPICallback
import com.app.omdbassignment.ui.movielist.datasource.MovieListRepositoryImpl


class MoviesViewModel(
    val moviesRepository: MovieListRepositoryImpl
) : ViewModel() {

    private var movieDetailResponse = MutableLiveData<MovieDetailResponse>()

    private var errorMessage = MutableLiveData<String>()

    fun getErrorMessageObserver(): MutableLiveData<String> {
        return errorMessage
    }

    fun getMovieDetailsResponseObserver(): MutableLiveData<MovieDetailResponse> {
        return movieDetailResponse
    }

    fun callMovieDetailAPI(movieName: String, apiKey: String) {

        moviesRepository.callMovieDetailAPI(
            movieName, apiKey,
            object : IAPICallback<MovieDetailResponse, String> {

                override fun onResponseSuccess(responseData: MovieDetailResponse) {
                    movieDetailResponse.value = responseData
                }

                override fun onResponseFailure(failureData: String) {
                    errorMessage.value = failureData
                }
            })
    }
}