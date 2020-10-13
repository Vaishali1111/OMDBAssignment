package com.app.omdbassignment.ui.moviedetail

import MovieDetailResponse
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.omdbassignment.networkapiclient.IAPICallback
import com.app.omdbassignment.ui.moviedetail.datasource.IMovieDetailRepository

class MovieDetailViewModel(
    val movieDetailRepository: IMovieDetailRepository
) : ViewModel() {

}