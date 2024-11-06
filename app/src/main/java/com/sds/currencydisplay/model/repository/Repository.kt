package com.sds.currencydisplay.model.repository

import android.content.Context
import android.widget.Toast
import com.sds.currencydisplay.model.api.RetrofitInstance
import com.sds.currencydisplay.model.modelCurrency.ModelCurrency
import retrofit2.Response

class Repository(private val context: Context) {

    /** асинхронная функция получения валюты */
    suspend fun getCurrency(): Response<ModelCurrency> {
        return RetrofitInstance.api.getCurrency()
    }

    /** функция показа всплывающего сообщения */
    fun showToast(message: String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}