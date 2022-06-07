package com.lib.mylibrary.ui.feature_spin_wheel.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.lib.mylibrary.R
import com.lib.mylibrary.core.util.Colors
import com.lib.mylibrary.core.util.TextLabels
import com.lib.mylibrary.databinding.ActivitySpinTheWheelBinding

class SpinTheWheelActivity : AppCompatActivity() {
    private val viewModel: SpinTheWheelViewModel by viewModels()
    private lateinit var binding: ActivitySpinTheWheelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spin_the_wheel)

        binding.lifecycleOwner = this
        binding.colors = Colors
        binding.textLabels = TextLabels
        binding.localColors = SpinTheWheelColors
    }
}