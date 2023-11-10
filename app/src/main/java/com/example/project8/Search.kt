package com.example.project8

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project8.databinding.FragmentSearchBinding

class Search : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by activityViewModels()

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater)
    {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar_feedback, menu)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId)
        {
            R.id.action_provideFeedback ->
            {
                sendFeedbackEmail()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun sendFeedbackEmail()
    {
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = "text/plain"
        val subject = "Feedback"
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        val recipient = "kenharpe@iu.edu"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
        startActivity(emailIntent)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        viewModel.errorHappened.observe(viewLifecycleOwner, Observer {value ->
            if (value != null) {
                Log.v("Search Fragment", value)
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        val toolbar = binding.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)

        binding.buttonClear.visibility = View.GONE

        binding.edittextMovieSearch.addTextChangedListener { text ->
            if (text != null)
            {
                if (text.isNotEmpty())
                {
                    binding.buttonClear.visibility = View.VISIBLE
                }
                else
                {
                    binding.buttonClear.visibility = View.GONE
                }
            }
        }

        binding.buttonSearch.setOnClickListener {
            viewModel.searchMovie(binding.edittextMovieSearch.text.toString())
        }

        binding.buttonClear.setOnClickListener {
            binding.edittextMovieSearch.setText("")
            viewModel.clearResults()
        }

        val adapter = MovieItemAdapter()
        binding.recyclerviewMovieList.layoutManager = LinearLayoutManager(context)
        binding.recyclerviewMovieList.adapter = adapter
        viewModel.movieList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

    }

    override fun onDestroy()
    {
        super.onDestroy()
        _binding = null
    }
}