package com.envios.kitabisa.ui.favorite

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.envios.kitabisa.BuildConfig
import com.envios.kitabisa.data.local.model.Favorite


class FavoriteItemViewModel (movie: Favorite?): ViewModel (){
    var title: ObservableField<String> = ObservableField()
    var poster : ObservableField<String> = ObservableField()
    var releaseDate : ObservableField<String> = ObservableField()
    var overview: ObservableField<String> = ObservableField()

    init {
        title.set(movie?.title)
        poster.set(BuildConfig.IMAGE_URL+movie?.posterPath)
        releaseDate.set(movie?.releaseDate)
        overview.set(movie?.overview)
    }
}