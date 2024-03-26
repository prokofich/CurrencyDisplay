package com.sds.currencydisplay.model.modelCurrency

data class ModelCurrency(
    val Date: String,
    val PreviousDate: String,
    val PreviousURL: String,
    val Timestamp: String,
    val Valute: Map<String,ModelCurrencyItem>
)