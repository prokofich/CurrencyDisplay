package com.sds.currencydisplay.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sds.currencydisplay.model.modelCurrency.ModelCurrency
import com.sds.currencydisplay.model.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MainViewModel:ViewModel() {

    private val repository = Repository()
    var currency: MutableLiveData<Response<ModelCurrency>> = MutableLiveData()

    fun getCurrency(){
        viewModelScope.launch(Dispatchers.IO) {
            val answer = repository.getCurrency()
            withContext(Dispatchers.Main){
                currency.value = answer
            }
        }
    }

}