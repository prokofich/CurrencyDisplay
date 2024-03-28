package com.sds.currencydisplay.model.modelCurrency

import com.google.gson.annotations.SerializedName

data class ModelCurrency(
    @SerializedName("Date") val date: String,
    @SerializedName("PreviousDate") val previousDate: String,
    @SerializedName("PreviousURL") val previousURL: String,
    @SerializedName("Timestamp") val timestamp: String,
    @SerializedName("Valute") val valute: Map<String, ModelCurrencyItem>
)