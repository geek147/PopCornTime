package com.envios.kitabisa.utils


open class ViewModelState
data class Success(var message: String?="") :ViewModelState()
data class Loading(var isLoading: Boolean) : ViewModelState()
data class Failed(var error: String?="") : ViewModelState()