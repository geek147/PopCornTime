package com.envios.kitabisa.ui.main

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.envios.kitabisa.BuildConfig
import com.envios.kitabisa.data.remote.model.Movie

class CarouselItemViewModel (movie: Movie?): ViewModel(){
    var title: ObservableField<String> = ObservableField()
    var poster : ObservableField<String> = ObservableField()


    init {
        title.set(movie?.title)
        poster.set(BuildConfig.IMAGE_URL+movie?.posterPath)
    }
}