package com.sds.currencydisplay.model.api

import com.sds.currencydisplay.model.modelCurrency.ModelCurrency
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    // асинхронная функция запроса получения валюты с сервера
    @GET("daily_json.js")
    suspend fun getCurrency(): Response<ModelCurrency>

}