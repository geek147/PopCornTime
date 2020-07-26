package com.envios.kitabisa.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.envios.kitabisa.data.remote.model.Genre
import com.envios.kitabisa.data.remote.model.Movie
import com.envios.kitabisa.data.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class MainViewModel(private val repository: MovieRepository) : ViewModel()  {

    val movies by lazy {
        MutableLiveData<List<Movie?>?>()
    }

    val genres by lazy {
        MutableLiveData<List<Genre?>?>()
    }


    val moviesByGenre by lazy {
        MutableLiveData<List<Movie?>?>()

    }


    fun getPopularMovie(page:String) {

        viewModelScope.launch(Dispatchers.Main) {
            movies.value = repository.getPopularMovies(page)

        }
    }

    fun getNowPlayingMovie(page:String) {
        viewModelScope.launch(Dispatchers.Main) {
            movies.value = repository.getNowPlayingMovie(page)

        }
    }

    fun geTopRatedMovie(page:String) {
        viewModelScope.launch(Dispatchers.Main) {
            movies.value = repository.getTopRatedMovie(page)

        }
    }

    fun getMovieGenres() {
        viewModelScope.launch(Dispatchers.Main) {
            genres.value = repository.getMovieGenres()

        }
    }

    fun getMoviesByGenre(withGenre: String) {
        viewModelScope.launch(Dispatchers.Main) {
            moviesByGenre.value = repository.getMoviesByGenre(withGenre)

        }
    }
}
