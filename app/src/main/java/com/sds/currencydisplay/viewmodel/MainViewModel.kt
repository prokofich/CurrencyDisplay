package com.sds.currencydisplay.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sds.currencydisplay.model.modelCurrency.ModelCurrency
import com.sds.currencydisplay.model.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repository = Repository(application)
    val currency: MutableLiveData<Response<ModelCurrency>> = MutableLiveData()

    fun getCurrency(){
        viewModelScope.launch(Dispatchers.IO) {
            val answer = repository.getCurrency()
            withContext(Dispatchers.Main){
                currency.value = answer
            }
        }
    }

}