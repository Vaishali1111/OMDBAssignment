package com.app.omdbassignment.ui.moviedetail

import MovieDetailResponse
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.app.omdbassignment.R
import com.app.omdbassignment.networkapiclient.APIClient
import com.app.omdbassignment.ui.moviedetail.datasource.MovieDetailRepositoryImpl
import com.app.omdbassignment.utility.ViewModelProviderFactory
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.content_movie_detail.*
import kotlinx.android.synthetic.main.test1.img_movie


class MovieDetailActivity : AppCompatActivity() {
    private lateinit var mMovieDetailViewModel: MovieDetailViewModel
    private lateinit var movieDetailResponse: MovieDetailResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val movieSearchAPI = APIClient.buildService(MovieDetailAPI::class.java)
        val factory = ViewModelProviderFactory(
            MovieDetailViewModel(MovieDetailRepositoryImpl(movieSearchAPI))
        )
        mMovieDetailViewModel =
            ViewModelProvider(this, factory).get(MovieDetailViewModel::class.java)

        movieDetailResponse = intent.getSerializableExtra("Result") as MovieDetailResponse

        if (movieDetailResponse != null) {
            setDataToView()
        }

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun setDataToView() {
        txt_movie_name.text = movieDetailResponse.title
        txt_movie_duration_category.text =
            "${movieDetailResponse.runtime} | ${movieDetailResponse.genre}"
        txt_movie_rating.text = "${movieDetailResponse.imdbRating}/10"
        txt_director.text = "${movieDetailResponse.director}"
        txt_released_date.text = "${movieDetailResponse.released}"
        txt_storyline.text = "${movieDetailResponse.plot}"

        Glide.with(this).load(movieDetailResponse.poster).into(img_movie)
    }


}
