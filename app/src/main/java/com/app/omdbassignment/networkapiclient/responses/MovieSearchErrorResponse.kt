package com.app.omdbassignment.networkapiclient.responses

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MovieSearchErrorResponse(
    @SerializedName("Response") val response: String,
    @SerializedName("Error") val error: String
) : Serializable