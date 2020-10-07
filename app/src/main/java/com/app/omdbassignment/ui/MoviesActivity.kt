package com.app.omdbassignment.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.omdbassignment.R

/*
* Created by vvv916942 on 05,October,2020
*/

class MoviesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        actionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
        }

    }

}