package com.hamza.deardiary.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hamza.deardiary.R
import com.hamza.deardiary.ui.fragments.LoginFragment
import com.hamza.deardiary.ui.fragments.MainFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * This is the activity the app opens up to.
 * It is used for login with [LoginFragment] and displaying all the available items and tags with [MainFragment].
 * It has the NavHostFragment for the nav graph used displaying and navigating for these 2 fragments.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }
}