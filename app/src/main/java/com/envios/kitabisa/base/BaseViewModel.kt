package com.envios.kitabisa.base

import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.envios.kitabisa.utils.Failed
import com.envios.kitabisa.utils.Loading
import com.envios.kitabisa.utils.Success
import com.envios.kitabisa.utils.ViewModelState

abstract class BaseViewModel : ViewModel() {

    private val loading = Loading(false)
    private var failed = Failed()
    private var success = Success()



    @CallSuper
    override fun onCleared() {
        super.onCleared()
    }

    fun showLoading(isLoading: Boolean, state:MutableLiveData<ViewModelState>){
        loading.isLoading = isLoading
        state.value = loading
    }

    fun showErrorMessage(message: String?=null, state:MutableLiveData<ViewModelState>){
        if(message!= null){
            failed.error = message
            state.value = failed
        }
    }

    fun showSuccessMessage(message:String?=null, state:MutableLiveData<ViewModelState>){
        success.message = message
        state.value = success
    }
}