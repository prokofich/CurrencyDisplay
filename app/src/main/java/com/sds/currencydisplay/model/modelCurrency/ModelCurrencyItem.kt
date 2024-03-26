package com.sds.currencydisplay.model.modelCurrency

data class ModelCurrencyItem(
    val CharCode: String,
    val ID: String,
    val Name: String,
    val Nominal: Int,
    val NumCode: String,
    val Previous: Double,
    val Value: Double
)
