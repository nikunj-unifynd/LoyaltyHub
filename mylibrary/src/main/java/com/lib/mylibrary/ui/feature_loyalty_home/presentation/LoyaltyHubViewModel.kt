package com.lib.mylibrary.ui.feature_loyalty_home.presentation

import androidx.lifecycle.ViewModel
import com.lib.mylibrary.data.remote.ApiClient

class LoyaltyHubViewModel: ViewModel() {

    private val apiService = ApiClient.initRetrofit()

}