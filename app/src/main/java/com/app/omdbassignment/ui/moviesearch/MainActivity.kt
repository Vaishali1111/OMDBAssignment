package com.app.omdbassignment.ui.moviesearch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.omdbassignment.R
import com.app.omdbassignment.networkapiclient.APIClient
import com.app.omdbassignment.ui.movielist.MoviesActivity
import com.app.omdbassignment.ui.movielist.datasource.MovieSearchRepositoryImpl
import com.app.omdbassignment.utility.Constants
import com.app.omdbassignment.utility.NetworkUtils
import com.app.omdbassignment.utility.UIUtils
import com.app.omdbassignment.utility.ViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var mMovieSearchViewModel: MovieSearchViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val movieSearchAPI = APIClient.buildService(MovieSearchAPI::class.java)
        val factory = ViewModelProviderFactory(
            MovieSearchViewModel(MovieSearchRepositoryImpl(movieSearchAPI))
        )
        mMovieSearchViewModel =
            ViewModelProvider(this, factory).get(MovieSearchViewModel::class.java)

        ObserverDataChanges()

        btn_search.setOnClickListener {
            searchMovie()
        }
    }

    private fun ObserverDataChanges() {
        mMovieSearchViewModel.getErrorMessageObserver().observe(this,
            Observer {
                UIUtils.hideProgressDialog()
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            })

        mMovieSearchViewModel.getMovieSearchResponseObserver().observe(this,
            Observer {
                UIUtils.hideProgressDialog()
                Log.e("response", it.toString())

                val intent: Intent = Intent(this, MoviesActivity::class.java)
                intent.putExtra("Result", it)
                intent.putExtra("SearchValue", edt_search_movie.text.toString())
                startActivity(intent)

            })
    }

    fun searchMovie() {
        if (NetworkUtils.isNetworkConnected(this)) {
            UIUtils.showProgressDialog(this)
            mMovieSearchViewModel.callMovieSearchAPI(
                edt_search_movie.text.toString(),
                Constants.API_KEY
            )
        } else {
            Toast.makeText(this, getString(R.string.action_check_network), Toast.LENGTH_SHORT)
                .show()
        }
    }


}
