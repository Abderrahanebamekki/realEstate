package com.example.myapplication



import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build

class NetworkControl(private val context: Context) {

    @SuppressLint("ObsoleteSdkInt")
    fun isConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val activeNetwork =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                // Add more cases as needed
                else -> false
            }
        } else {
            return isNetworkConnectedBelowMarshmallow(connectivityManager)
        }
    }

    @Suppress("DEPRECATION")
    private fun isNetworkConnectedBelowMarshmallow(connectivityManager: ConnectivityManager): Boolean {
        val networks: Array<Network> = connectivityManager.allNetworks
        for (network in networks) {
            val networkInfo = connectivityManager.getNetworkInfo(network)
            if (networkInfo?.isConnected == true) {
                return true
            }
        }
        return false
    }
}
