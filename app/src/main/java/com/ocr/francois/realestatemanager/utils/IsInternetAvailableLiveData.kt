@file:Suppress("DEPRECATION")

package com.ocr.francois.realestatemanager.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.net.NetworkRequest
import androidx.lifecycle.LiveData

class IsInternetAvailableLiveData(private val context: Context) : LiveData<Boolean>() {

    private var connectivityManager: ConnectivityManager? = null
    private var networkRequestBuilder: NetworkRequest.Builder? = null
    private var networkCallback: ConnectivityManager.NetworkCallback? = null

    private var networkReceiver: BroadcastReceiver? = null

    init {
        postValue(false)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            networkRequestBuilder = NetworkRequest.Builder()
            networkCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    postValue(true)
                }

                override fun onLost(network: Network) {
                    postValue(false)
                }
            }
        } else {
            networkReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    if (intent.extras != null) {
                        val activeNetwork =
                            intent.extras!![ConnectivityManager.EXTRA_NETWORK_INFO] as NetworkInfo?
                        postValue(
                            activeNetwork != null &&
                                    activeNetwork.isConnectedOrConnecting
                        )
                    }
                }
            }
        }
    }

    override fun onActive() {
        super.onActive()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            connectivityManager!!.registerNetworkCallback(
                networkRequestBuilder!!.build(),
                networkCallback!!
            )
        } else {
            val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            context.registerReceiver(networkReceiver, filter)
        }
    }

    override fun onInactive() {
        super.onInactive()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            connectivityManager!!.unregisterNetworkCallback(networkCallback!!)
        } else {
            context.unregisterReceiver(networkReceiver)
        }
    }
}