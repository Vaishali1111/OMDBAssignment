package com.app.omdbassignment.ui.movielist

import MovieSearchResponse
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.omdbassignment.R
import com.app.omdbassignment.networkapiclient.APIClient
import com.app.omdbassignment.ui.moviedetail.MovieDetailAPI
import com.app.omdbassignment.ui.moviedetail.MovieDetailActivity
import com.app.omdbassignment.ui.movielist.adapter.MovieListAdapter
import com.app.omdbassignment.ui.movielist.datasource.MovieListRepositoryImpl
import com.app.omdbassignment.utility.Constants
import com.app.omdbassignment.utility.NetworkUtils
import com.app.omdbassignment.utility.UIUtils
import com.app.omdbassignment.utility.ViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_movies.*


class MoviesActivity : AppCompatActivity() {

    private lateinit var mMoviesViewModel: MoviesViewModel
    private lateinit var movieListAdapter: MovieListAdapter
    private lateinit var movieSearchReponse: MovieSearchResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)


        movieSearchReponse = intent.getSerializableExtra("Result") as MovieSearchResponse
        val searchValue = intent.getStringExtra("SearchValue")

        val movieDetailAPI = APIClient.buildService(MovieDetailAPI::class.java)
        val factory = ViewModelProviderFactory(
            MoviesViewModel(MovieListRepositoryImpl(movieDetailAPI))
        )
        mMoviesViewModel = ViewModelProvider(this, factory).get(MoviesViewModel::class.java)


        ObserverDataChanges()

        setAdapter()

        if (movieSearchReponse != null) {
            movieListAdapter.addAll(movieSearchReponse.search)
            movieListAdapter.notifyDataSetChanged()

            supportActionBar?.let {
                it.title = "$searchValue"
                it.setDisplayHomeAsUpEnabled(true)
                it.setDisplayShowHomeEnabled(true)
            }
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

    fun setAdapter() {
        val linearLayoutManager = LinearLayoutManager(this)

        rv_movies?.let {
            it.layoutManager = linearLayoutManager
        }

        movieListAdapter = MovieListAdapter(this, object :
            MovieSelectListener {
            override fun movieSelected(movie_position: Int) {
                getMovieDetail(movieSearchReponse.search.get(movie_position).title)
            }
        })
        rv_movies.adapter = movieListAdapter

    }

    private fun ObserverDataChanges() {
        mMoviesViewModel.getErrorMessageObserver().observe(this,
            Observer {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            })

        mMoviesViewModel.getMovieDetailsResponseObserver().observe(this,
            Observer {
                UIUtils.hideProgressDialog()
                Log.e("response", it.toString())

                val intent: Intent = Intent(this, MovieDetailActivity::class.java)
                intent.putExtra("Result", it)
                startActivity(intent)

            })
    }


    fun getMovieDetail(movieName: String) {
        if (NetworkUtils.isNetworkConnected(this)) {
            UIUtils.showProgressDialog(this)
            mMoviesViewModel.callMovieDetailAPI(
                movieName,
                Constants.API_KEY
            )
        } else {
            Toast.makeText(this, getString(R.string.action_check_network), Toast.LENGTH_SHORT)
                .show()
        }
    }

}