package com.example.project8

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("Runtime") val runtime: String?,
    @SerializedName("Genre") val genre: String?,
    @SerializedName("Poster") val poster: String,
    @SerializedName("Ratings") val ratings: List<Score>?,
    @SerializedName("imdbRating") val imdbRating: String?,
    @SerializedName("imdbID") val imdbID: String
)

data class Score(
    @SerializedName("Source") val source: String,
    @SerializedName("Rating") val rating: String
)