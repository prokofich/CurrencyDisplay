package com.sds.currencydisplay.model.repository

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import com.sds.currencydisplay.model.api.RetrofitInstance
import com.sds.currencydisplay.model.modelCurrency.ModelCurrency
import retrofit2.Response

class Repository {

    // асинхронная функция получения валюты
    suspend fun getCurrency(): Response<ModelCurrency> {
        return RetrofitInstance.api.getCurrency()
    }

    // функция показа всплывающего сообщения
    fun showToast(context:Context,message:String){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }

}