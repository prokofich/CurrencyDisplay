package com.sds.currencydisplay.model.modelCurrency

import com.google.gson.annotations.SerializedName

data class ModelCurrencyItem(
    @SerializedName("CharCode") val charCode: String,
    @SerializedName("ID") val id: String,
    @SerializedName("Name") val name: String,
    @SerializedName("Nominal") val nominal: Int,
    @SerializedName("NumCode") val numCode: String,
    @SerializedName("Previous") val previous: Double,
    @SerializedName("Value") val value: Double
)
