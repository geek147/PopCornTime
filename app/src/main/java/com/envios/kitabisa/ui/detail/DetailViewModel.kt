package com.envios.kitabisa.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.envios.kitabisa.base.BaseViewModel
import com.envios.kitabisa.data.local.model.Favorite
import com.envios.kitabisa.data.remote.model.MovieDetail
import com.envios.kitabisa.data.remote.model.Review
import com.envios.kitabisa.data.repository.MovieRepository
import com.envios.kitabisa.utils.ErrorUtils
import com.envios.kitabisa.utils.Loading
import com.envios.kitabisa.utils.ViewModelState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailViewModel (private val repository: MovieRepository) : BaseViewModel()  {

    data class movieDetailLoaded(val movieDetail:MovieDetail? = null) : ViewModelState()
    data class reviewsLoaded(val reviews:List<Review?>? = null) : ViewModelState()


    val reviews by lazy {
        MutableLiveData<ViewModelState>()
    }

    val movieDetail by lazy {
        MutableLiveData <ViewModelState>()
    }

    val isFavorite by lazy{
        MutableLiveData <Favorite?> ()
    }


    fun getReviews(movie_id:String) {

        reviews.value = Loading(true)
        viewModelScope.launch(Dispatchers.Main) {
            try{
                val response = repository.getMovieReviews(movie_id)
                reviews.value = DetailViewModel.reviewsLoaded(response)
            }catch (t : Throwable) {
                showErrorMessage(ErrorUtils.getErrorThrowableMsg(t),reviews)
            }

        }

    }

    fun getMovieDetail(movie_id: String) {

        movieDetail.value = Loading(true)
        viewModelScope.launch(Dispatchers.Main) {
            try{
                val response = repository.getMovieDetail(movie_id)
                movieDetail.value = DetailViewModel.movieDetailLoaded(response)
            }catch (t : Throwable) {
                showErrorMessage(ErrorUtils.getErrorThrowableMsg(t), movieDetail)
            }

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
