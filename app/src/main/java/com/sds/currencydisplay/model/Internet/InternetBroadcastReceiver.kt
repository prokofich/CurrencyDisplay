package com.sds.currencydisplay.model.Internet

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

class InternetBroadcastReceiver(private val interfaceForActivity:InterfaceForActivity): BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            val isConnected = checkInternet(context)
            interfaceForActivity.showChangeInternet(isConnected)
        }
    }

    private fun checkInternet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnected ?: false
    }

}