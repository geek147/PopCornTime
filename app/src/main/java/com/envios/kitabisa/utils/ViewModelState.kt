package com.envios.kitabisa.utils

/**
 * Created by chirikualii on 11/12/18
 * github.com/chirikualii
 */
data class ViewModelState (
    var loading: Boolean = false,
    var error: Exception? = null,
    var data: MutableList<Any>? = null
)