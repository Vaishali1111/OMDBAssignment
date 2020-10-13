package com.app.omdbassignment.ui.movielist.adapter

import Search
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.app.omdbassignment.R
import com.app.omdbassignment.ui.movielist.MovieSelectListener
import com.bumptech.glide.Glide

class MovieListAdapter(val context: Context, val movieSelectListener: MovieSelectListener) :
    RecyclerView.Adapter<MovieListAdapter.MyViewHolder>() {

    private var mMovieList: ArrayList<Search> = ArrayList()
    private var isLoadingAdded = false
    private var totalCount = 0

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_movie, parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return mMovieList.size
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        val result = mMovieList[position]

        viewHolder.txt_movie_name?.text = result.title
        viewHolder.txt_movie_release_year?.text = result.year

        viewHolder.img_movie_poster?.let { img_movie ->
            result.poster?.let { poster ->
                Glide.with(context).load(poster).placeholder(R.drawable.icon_movie).into(img_movie)
            }
        }

        viewHolder.card_movie?.setOnClickListener {
            movieSelectListener.movieSelected(position)
        }
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var img_movie_poster: ImageView? = null
        var txt_movie_name: AppCompatTextView? = null
        var txt_movie_release_year: AppCompatTextView? = null
        var card_movie: CardView? = null


        init {
            img_movie_poster = view.findViewById(R.id.img_movie_poster)
            txt_movie_name = view.findViewById(R.id.txt_movie_name)
            txt_movie_release_year = view.findViewById(R.id.txt_movie_release_year)
            card_movie = view.findViewById(R.id.card_movie)
        }
    }

    fun add(movie: Search) {
        mMovieList.add(movie)
        notifyItemInserted(mMovieList.size - 1)
    }

    fun addAll(movieList: List<Search>) {
        mMovieList.addAll(movieList)
        notifyDataSetChanged()
    }

    fun clear() {
        isLoadingAdded = false
        try {
            mMovieList.clear()
        } catch (e: ArrayIndexOutOfBoundsException) {
        }
    }


}