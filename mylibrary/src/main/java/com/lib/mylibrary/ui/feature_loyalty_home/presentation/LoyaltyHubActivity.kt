package com.lib.mylibrary.ui.feature_loyalty_home.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.lib.mylibrary.R
import com.lib.mylibrary.core.util.Colors
import com.lib.mylibrary.core.util.TextLabels
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
        binding.colors = Colors
        binding.textLabels = TextLabels
        binding.localColors = LoyaltyHubHomeColors
        binding.localTextLabels = LoyaltyHubHomeTextLabels
        binding.localImages = LoyaltyHubHomeImages

    }
}