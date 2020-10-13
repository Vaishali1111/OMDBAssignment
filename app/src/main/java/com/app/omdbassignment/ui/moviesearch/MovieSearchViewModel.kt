package com.app.omdbassignment.ui.moviesearch

import MovieSearchResponse
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.omdbassignment.networkapiclient.IAPICallback
import com.app.omdbassignment.ui.movielist.datasource.IMovieSearchRepository


class MovieSearchViewModel(
    val moviesRepository: IMovieSearchRepository
) : ViewModel() {

    private var movieSearchResponse = MutableLiveData<MovieSearchResponse>()

    private var errorMessage = MutableLiveData<String>()

    fun getErrorMessageObserver(): MutableLiveData<String> {
        return errorMessage
    }

    fun getMovieSearchResponseObserver(): MutableLiveData<MovieSearchResponse> {
        return movieSearchResponse
    }

    fun callMovieSearchAPI(movieName: String, apiKey: String) {

        moviesRepository.callMovieSearchAPI(
            movieName, apiKey,
            object : IAPICallback<MovieSearchResponse, String> {

                override fun onResponseSuccess(responseData: MovieSearchResponse) {
                    movieSearchResponse.value = responseData
                }

                override fun onResponseFailure(failureData: String) {
                    errorMessage.value = failureData
                }
            })
    }

}