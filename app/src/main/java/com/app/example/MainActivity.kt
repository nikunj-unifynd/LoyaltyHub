package com.app.example

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.lib.mylibrary.core.util.Constants
import com.lib.mylibrary.core.util.TextLabels
import com.lib.mylibrary.ui.feature_loyalty_home.presentation.LoyaltyHubActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            Intent(this, LoyaltyHubActivity::class.java).apply {
                startActivity(this)
            }
        }
    }
}