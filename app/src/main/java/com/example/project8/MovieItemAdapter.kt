package com.example.project8

import com.bumptech.glide.Glide
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.project8.databinding.MovieItemBinding

class MovieItemAdapter
    : ListAdapter<Movie, MovieItemAdapter.MovieItemViewHolder>(MovieDiffItemCallback())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : MovieItemViewHolder = MovieItemViewHolder.inflateFrom(parent)
    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int)
    {
        val item = getItem(position)
        holder.bind(item)
    }

    class MovieItemViewHolder(private val binding: MovieItemBinding)
        : RecyclerView.ViewHolder(binding.root)
    {
        companion object
        {
            fun inflateFrom(parent: ViewGroup) : MovieItemViewHolder
            {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MovieItemBinding.inflate(layoutInflater, parent, false)
                return MovieItemViewHolder(binding)
            }
        }

        fun bind(movie: Movie)
        {
            binding.textviewTitle.text = movie.title ?: ""
            if (movie.year.isNullOrBlank())
            {
                binding.textviewYear.visibility = View.GONE
            }
            else
            {
                binding.textviewYear.text = "Released: ${movie.year}"
                binding.textviewYear.visibility = View.VISIBLE
            }

            if (movie.runtime.isNullOrBlank())
            {
                binding.textviewRuntime.visibility = View.GONE
            }
            else
            {
                binding.textviewRuntime.text = "Runtime: ${movie.runtime}"
                binding.textviewRuntime.visibility = View.VISIBLE
            }

            if (movie.genre.isNullOrBlank())
            {
                binding.textviewGenre.visibility = View.GONE
            }
            else
            {
                binding.textviewGenre.text = "Genre: ${movie.genre}"
                binding.textviewGenre.visibility = View.VISIBLE
            }

            if (movie.imdbRating.isNullOrBlank())
            {
                binding.textviewImdbRating.visibility = View.GONE
            }
            else
            {
                binding.textviewImdbRating.text = "IMDb Score: ${movie.imdbRating}"
                binding.textviewImdbRating.visibility = View.VISIBLE
            }
            if (movie.ratings.isNullOrEmpty())
            {
                binding.textviewRatings.visibility = View.GONE
            }
            else
            {
                binding.textviewRatings.text = movie.ratings.joinToString(separator = "\n") { score -> "${score.source}: ${score.rating}" }
                binding.textviewRatings.visibility = View.VISIBLE
            }

            if (movie.poster.isNullOrEmpty())
            {
                binding.imageviewPoster.visibility = View.GONE
            }
            else
            {
                Glide.with(this.itemView.context).load(movie.poster).into(binding.imageviewPoster)
            }

            binding.textviewImdbLink.text = itemView.context.getString(R.string.imdb_page)
            binding.textviewImdbRating.setOnClickListener {
                val imdbIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.imdb.com/title/${movie.imdbID}/"))
                itemView.context.startActivity(imdbIntent)
            }

            binding.imageButtonShare.setOnClickListener {
                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "${movie.title}: ${"https://www.imdb.com/title/${movie.imdbID}/"}")
                    type = "text/plain"
                }
                itemView.context.startActivity(Intent.createChooser(shareIntent, "Share via"))
            }
        }
    }
}
