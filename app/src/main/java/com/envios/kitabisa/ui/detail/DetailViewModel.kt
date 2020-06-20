package com.envios.kitabisa.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.envios.kitabisa.data.local.model.Favorite
import com.envios.kitabisa.data.remote.model.MovieDetail
import com.envios.kitabisa.data.remote.model.Review
import com.envios.kitabisa.data.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailViewModel (private val repository: MovieRepository) : ViewModel()  {

    val reviews by lazy {
        MutableLiveData<List<Review?>?>()
    }

    val movieDetail by lazy {
        MutableLiveData <MovieDetail?>()
    }

    val isFavorite by lazy{
        MutableLiveData <Favorite?> ()
    }


    fun getReviews(movie_id:String) {

        viewModelScope.launch(Dispatchers.Main) {
            reviews.value = repository.getMovieReviews(movie_id)

        }
    }

    fun getMovieDetail(movie_id: String) {
        viewModelScope.launch(Dispatchers.Main) {
            movieDetail.value = repository.getMovieDetail(movie_id)

        }
    }

    fun insertMovieToFavorite(movieDetail: MovieDetail?) {
        viewModelScope.launch {
            repository.insertFavoriteMovie(movieDetail)

        }
    }


    fun deleteMovieFromFavorite(id:Int) {
        viewModelScope.launch {
            repository.deleteFavoriteMovie(id)
        }
    }


    fun checkIsFavoriteMovie(id:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            isFavorite.postValue(repository.checkIsFavorite(id))
        }
    }


}
