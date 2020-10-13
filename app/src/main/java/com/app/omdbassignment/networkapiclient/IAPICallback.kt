package com.app.omdbassignment.networkapiclient

interface IAPICallback<T, V> {

// gives callback if get response success

    fun onResponseSuccess(responseData: T)

    // gives callback if get response failure

    fun onResponseFailure(failureData: V)

}