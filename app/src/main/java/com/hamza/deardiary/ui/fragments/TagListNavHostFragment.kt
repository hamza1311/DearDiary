package com.hamza.deardiary.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hamza.deardiary.R

/**
 * Fragment for holding NavHostFragment for Tags list. This is the 2nd fragment displayed in viewpager in [MainFragment]
 */
class TagListNavHostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tag_list_nav_host, container, false)
    }
}
