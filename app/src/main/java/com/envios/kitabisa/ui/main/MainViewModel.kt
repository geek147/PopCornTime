package com.envios.kitabisa.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.envios.kitabisa.base.BaseViewModel
import com.envios.kitabisa.data.remote.model.Genre
import com.envios.kitabisa.data.remote.model.Movie
import com.envios.kitabisa.data.repository.MovieRepository
import com.envios.kitabisa.utils.ErrorUtils
import com.envios.kitabisa.utils.Loading
import com.envios.kitabisa.utils.ViewModelState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel(private val repository: MovieRepository) : BaseViewModel()  {

    data class moviesLoaded(val movieList: List<Movie?>? = null) : ViewModelState()
    data class genreLoaded(val genreList: List<Genre?>? = null) : ViewModelState()


    val movies by lazy {
        MutableLiveData<ViewModelState>()
    }

    val genres by lazy {
        MutableLiveData<ViewModelState>()
    }


    val moviesByGenre by lazy {
        MutableLiveData<ViewModelState>()

    }


    fun getPopularMovie(page:String) {

        movies.value = Loading(true)

        viewModelScope.launch(Dispatchers.Main) {
            try{
                val response = repository.getPopularMovies(page)
                movies.value = moviesLoaded(response)
            }catch (t : Throwable) {
                showErrorMessage(ErrorUtils.getErrorThrowableMsg(t), movies)

            }

        }
    }

    fun getNowPlayingMovie(page:String) {
        movies.value = Loading(true)

        viewModelScope.launch(Dispatchers.Main) {
            try{
                val response = repository.getNowPlayingMovie(page)
                movies.value = moviesLoaded(response)
            }catch (t : Throwable) {
                showErrorMessage(ErrorUtils.getErrorThrowableMsg(t), movies)

            }

        }
    }

    fun geTopRatedMovie(page:String) {
        movies.value = Loading(true)

        viewModelScope.launch(Dispatchers.Main) {
            try{
                val response = repository.getTopRatedMovie(page)
                movies.value = moviesLoaded(response)
            }catch (t : Throwable) {
                showErrorMessage(ErrorUtils.getErrorThrowableMsg(t), movies)

            }

        }
    }

    fun getMovieGenres() {
        genres.value = Loading(true)
        viewModelScope.launch(Dispatchers.Main) {
            try{
                val response = repository.getMovieGenres()
                genres.value = genreLoaded(response)
            }catch (t : Throwable) {
                showErrorMessage(ErrorUtils.getErrorThrowableMsg(t), genres)
            }

        }
    }

    fun getMoviesByGenre(withGenre: String, page: String) {
        moviesByGenre.value = Loading(true)
        viewModelScope.launch(Dispatchers.Main) {
            try{
                val response = repository.getMoviesByGenre(withGenre, page)
                moviesByGenre.value = moviesLoaded(response)
            }catch (t : Throwable) {
                showErrorMessage(ErrorUtils.getErrorThrowableMsg(t), moviesByGenre)
            }

        }
    }
}
