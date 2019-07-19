package com.hamza.deardiary.ui.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.hamza.deardiary.R
import com.hamza.deardiary.util.showSnackbar

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_xml, rootKey)

        val setPasswordPref = findPreference("setPassword")

        findPreference("enablePasswordPreference").apply {
            setOnPreferenceChangeListener { _, newValue ->
                showSnackbar(getString(R.string.password_enabled))
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

        findPreference("enableSignaturePreference").apply {
            setOnPreferenceChangeListener { _, newValue ->
                showSnackbar(getString(R.string.password_enabled))
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
