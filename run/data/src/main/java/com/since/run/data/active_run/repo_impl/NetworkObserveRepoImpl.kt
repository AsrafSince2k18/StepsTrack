package com.since.run.data.active_run.repo_impl

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.since.run.domain.repo.NetworkObserveRepo
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class NetworkObserveRepoImpl(
    private val context: Context
) : NetworkObserveRepo {


    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun observeNetwork(): Flow<Boolean> {
        return callbackFlow {


            val networkCallBack = object : ConnectivityManager.NetworkCallback(){

                override fun onUnavailable() {
                    super.onUnavailable()
                    trySend(false)
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    trySend(false)
                }

                override fun onCapabilitiesChanged(
                    network: Network,
                    networkCapabilities: NetworkCapabilities
                ) {
                    super.onCapabilitiesChanged(network, networkCapabilities)

                    val status = networkCapabilities
                        .hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    trySend(status)
                }
            }

            val request = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                .build()

            connectivityManager.registerNetworkCallback(request,networkCallBack)

            awaitClose {
                connectivityManager.unregisterNetworkCallback(networkCallBack)
            }

        }
    }
}