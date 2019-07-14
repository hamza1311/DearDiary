package com.hamza.deardiary.ui.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.hamza.deardiary.R


class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_xml, rootKey)
    }
}
