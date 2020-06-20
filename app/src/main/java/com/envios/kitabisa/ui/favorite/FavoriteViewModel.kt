package com.envios.kitabisa.ui.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.envios.kitabisa.data.local.model.Favorite
import com.envios.kitabisa.data.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FavoriteViewModel (private val repository: MovieRepository) : ViewModel()  {

    val favorites by lazy {
        MutableLiveData<List<Favorite?>?>()
    }

    fun getListFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            favorites.postValue(repository.getAllUsersFromLocal())
        }
    }
}
