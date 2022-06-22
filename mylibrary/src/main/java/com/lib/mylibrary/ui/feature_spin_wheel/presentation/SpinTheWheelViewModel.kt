package com.lib.mylibrary.ui.feature_spin_wheel.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lib.mylibrary.core.util.UIState
import com.lib.mylibrary.data.remote.ApiClient
import com.lib.mylibrary.data.remote.customnetworkadapter.NetworkResponse
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class SpinTheWheelViewModel: ViewModel() {

    private val apiService = ApiClient.initRetrofit()

    private val TAG = this::class.java.simpleName

    private val uiStateChannel = Channel<UIState>(Channel.BUFFERED)
    val uiStateFlow = uiStateChannel.receiveAsFlow()

    fun getSpinWheel(
        id: String,
    ) {
        viewModelScope.launch {
            viewModelScope.launch { uiStateChannel.send(UIState.Loading) }
            val response = apiService.getSpinWheelById(id)
            when (response) {
                is NetworkResponse.Success -> {
                    if (response.body.status.code != 200) {
                        uiStateChannel.send(UIState.Error(response.body.status.message ?: ""))
                    } else {
                        uiStateChannel.send(UIState.Success(response.body.data))
                        Timber.d("Spin Wheel data: ${response.body.data.spinWheels}")
                    }
                }

                is NetworkResponse.ServerError -> {
                    uiStateChannel.send(UIState.Error(response.body?.status?.message ?: ""))
                    Timber.d("Server Error")
                }

                is NetworkResponse.NetworkError -> {
                    uiStateChannel.send(UIState.Error("NetworkError"))
                    Timber.d("Network Error")
                }

                is NetworkResponse.UnknownError -> {
                    uiStateChannel.send(UIState.Error("Unknown Error"))
                    Timber.d("Unknown Error")
                }

            }
        }
    }

    fun buySpinWheel(
        id: String,
    ) {
        viewModelScope.launch {
            viewModelScope.launch { uiStateChannel.send(UIState.Loading) }
            val response = apiService.buySpinWheel(id)
            when (response) {
                is NetworkResponse.Success-> {
                    if (response.body.status.code != 200) {
                        uiStateChannel.send(UIState.Error(response.body.status.message ?: ""))
                    } else {
                        uiStateChannel.send(UIState.Success(response.body.data))
                    }
                }

                is NetworkResponse.ServerError -> {
                    uiStateChannel.send(UIState.Error(response.body?.status?.message ?: ""))
                    Timber.d("Server Error")
                }

                is NetworkResponse.NetworkError -> {
                    uiStateChannel.send(UIState.Error("NetworkError"))
                    Timber.d("Network Error")
                }

                is NetworkResponse.UnknownError -> {
                    uiStateChannel.send(UIState.Error("Unknown Error"))
                    Timber.d("Unknown Error")
                }

            }
        }
    }

}