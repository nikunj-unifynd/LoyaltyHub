package com.lib.mylibrary.ui.feature_loyalty_home.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.lib.mylibrary.R
import com.lib.mylibrary.databinding.ActivityLoyaltyHubBinding

class LoyaltyHubActivity : AppCompatActivity() {

    private val viewModel: LoyaltyHubViewModel by viewModels()
    private lateinit var binding: ActivityLoyaltyHubBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_loyalty_hub
        )
        binding.lifecycleOwner = this
        binding.colorsYou = "#E8C653"
        binding.textLabels = "Hello"
        //binding.executePendingBindings()

    }
}