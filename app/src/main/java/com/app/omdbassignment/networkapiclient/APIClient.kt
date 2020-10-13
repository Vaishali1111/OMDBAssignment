package com.app.omdbassignment.networkapiclient

import com.app.omdbassignment.utility.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIClient {

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(getLogger().build())
        .build()

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }

    private fun getLogger(): OkHttpClient.Builder {
        val logging: HttpLoggingInterceptor = HttpLoggingInterceptor()
        var httpClient = OkHttpClient.Builder()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        httpClient.addInterceptor(logging)
        return httpClient
    }
}
