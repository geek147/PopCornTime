package com.envios.kitabisa.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.envios.kitabisa.base.BaseViewModel
import com.envios.kitabisa.data.remote.model.Movie
import com.envios.kitabisa.data.repository.MovieRepository
import com.envios.kitabisa.utils.ErrorUtils
import com.envios.kitabisa.utils.Loading
import com.envios.kitabisa.utils.ViewModelState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieListViewModel(private val repository: MovieRepository) : BaseViewModel()  {

    data class moviesLoaded(val movieList: List<Movie?>? = null) : ViewModelState()


    val moviesByGenre by lazy {
        MutableLiveData<ViewModelState>()

    }


    fun getMoviesByGenre(withGenre: String, page:String) {
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