package com.envios.kitabisa.ui.detail

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.envios.kitabisa.data.remote.model.Review


class ReviewItemViewModel (review: Review?): ViewModel(){
    var author: ObservableField<String> = ObservableField()
    var content : ObservableField<String> = ObservableField()

    init {
        author.set(review?.author)
        content.set(review?.content)


    }
}