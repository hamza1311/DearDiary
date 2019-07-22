package com.hamza.deardiary.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hamza.deardiary.R
import com.hamza.deardiary.ui.fragments.SettingsFragment

/**
 * This activity shows the nav host fragment which is responsible for displaying [SettingsFragment] with its respective nav graph
 */
class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }
}
