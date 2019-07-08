package com.hamza.deardiary.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.hamza.deardiary.ui.fragments.ItemsListFragment
import com.hamza.deardiary.ui.fragments.TagListNavHostFragment

class PagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ItemsListFragment()
            1 -> TagListNavHostFragment()
            else -> null!!
        }
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "All items"
            1 -> "Tags"
            else -> null
        }
    }
}