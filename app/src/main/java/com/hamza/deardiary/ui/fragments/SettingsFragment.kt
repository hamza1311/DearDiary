package com.hamza.deardiary.ui.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.google.android.material.snackbar.Snackbar
import android.app.Activity
import android.view.View
import com.hamza.deardiary.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_xml, rootKey)

        val setPasswordPref = findPreference("setPassword")

        findPreference("enablePasswordPreference").apply {
            setOnPreferenceChangeListener { _, newValue ->
                showSnackbar(newValue.toString())
                setPasswordPref.isEnabled = newValue as Boolean
                true
            }
        }

        setPasswordPref.apply {
            setOnPreferenceChangeListener { _, newValue ->
                showSnackbar(newValue.toString())
                true
            }
        }
    }
}

fun PreferenceFragmentCompat.showSnackbar(text: String) {
    this.activity?.run {
        Snackbar.make(this.findViewById<View>(android.R.id.content), text, Snackbar.LENGTH_LONG).show()
    }
}