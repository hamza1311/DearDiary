package com.hamza.deardiary.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hamza.deardiary.R
import com.hamza.deardiary.ui.fragments.AddViewUpdateItemFragment
import com.hamza.deardiary.ui.fragments.ItemDetailsFragment

/**
 * This activity is used for letting the user Create, Update and View the item. These operations are performed by [AddViewUpdateItemFragment].
 * This activity also shows [ItemDetailsFragment] which shows, well details for the selected item.
 * It has the NavHostFragment for the nav graph used for these 2 fragments
 */
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
