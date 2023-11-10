package com.example.project8

import androidx.recyclerview.widget.DiffUtil

class MovieDiffItemCallback : DiffUtil.ItemCallback<Movie>()
{
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = (oldItem.imdbID == newItem.imdbID)
    override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = (oldItem == newItem)
}