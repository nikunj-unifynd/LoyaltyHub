package com.lib.mylibrary.ui.feature_spin_wheel.presentation

import androidx.lifecycle.ViewModel
import com.lib.mylibrary.data.remote.ApiClient

class SpinTheWheelViewModel: ViewModel() {

    private val apiService = ApiClient.initRetrofit()

}