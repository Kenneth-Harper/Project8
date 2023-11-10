package com.example.project8

data class MovieApiResponse(
    val Search: List<Movie>?,
    val totalResults: String?,
    val Response: String,
    val error: String?
)