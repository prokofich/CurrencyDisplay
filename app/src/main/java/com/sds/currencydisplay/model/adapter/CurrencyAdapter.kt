package com.sds.currencydisplay.model.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sds.currencydisplay.R
import com.sds.currencydisplay.model.modelCurrency.ModelCurrencyItem

class CurrencyAdapter : RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    private var listCurrency = emptyList<ModelCurrencyItem>()

    class CurrencyViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_currency_for_rv, parent, false)
        return CurrencyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listCurrency.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val textViewTitle = holder.itemView.findViewById<TextView>(R.id.id_item_tv_title)
        val textViewName = holder.itemView.findViewById<TextView>(R.id.id_item_tv_name)
        val textViewPrice = holder.itemView.findViewById<TextView>(R.id.id_item_tv_price)

        textViewTitle.text = listCurrency[position].charCode
        textViewName.text = listCurrency[position].name
        textViewPrice.text = "${listCurrency[position].value} руб"
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<ModelCurrencyItem>?) {
        if (list != null) {
            listCurrency = list
            notifyDataSetChanged()
        }
    }

}