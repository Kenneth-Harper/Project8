package com.example.project8

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class SearchViewModel : ViewModel()
{
    val apiKey = "efd1efa9"

    val _movieList = MutableLiveData<List<Movie>?>()
    val movieList : LiveData<List<Movie>?> get() = _movieList

    private val _errorHappened = MutableLiveData<String?>()
    val errorHappened: LiveData<String?> get() = _errorHappened

    var isLoading = false

    fun searchMovie(title: String)
    {
        viewModelScope.launch {
            isLoading = true
            try
            {
                val response = RetrofitClient.retrofitService.getMovieDetails(title, apiKey)
                Log.v("SearchViewModel", response.toString())
                parseResponse(response)

            }
            catch (e: IOException)
            {
                _errorHappened.value = "Check your internet connection"
            } catch (e: HttpException)
            {
                _errorHappened.value = "HTTP error: ${e.code()}"
            } catch (e: Exception)
            {
                _errorHappened.value = e.message ?: "An unknown error occurred"
            } finally
            {
                isLoading = false
            }
        }
    }

    private fun parseResponse(response: Response<*>)
    {
        if (response.isSuccessful)
        {
            Log.v("SearchViewModel", "Response is successful")
            val body = response.body()

            if (body is MovieApiResponse)
            {
                Log.v("SearchViewModel","Response is MovieApiResponse")
                _movieList.value = body.Search
            }
        }
        else
        {
            _errorHappened.value = response.body().toString() ?: "An unknown error occurred"
        }
    }

    fun clearResults()
    {
        _movieList.value = emptyList()
        _errorHappened.value = null
    }

}