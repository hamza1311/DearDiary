package com.hamza.deardiary.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hamza.deardiary.R

class NewUpdateOrViewDiaryItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_update_or_view_diary_item)
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}
