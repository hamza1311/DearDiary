package com.hamza.deardiary.ui.fragments

import android.os.Bundle
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.hamza.deardiary.R
import com.hamza.deardiary.util.showSnackbar

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_xml, rootKey)

        val setPasswordPref = findPreference<EditTextPreference>("setPassword")

        findPreference<SwitchPreferenceCompat>("enablePasswordPreference")?.apply {
            setOnPreferenceChangeListener { _, newValue ->
                showSnackbar(getString(R.string.password_enabled))
                setPasswordPref?.isEnabled = newValue as Boolean
                true
            }
        }

        setPasswordPref?.apply {
            setOnPreferenceChangeListener { _, newValue ->
                showSnackbar(newValue.toString())
                true
            }
        }

        val setSignaturePreference = findPreference<EditTextPreference>("setSignature")

        findPreference<SwitchPreferenceCompat>("enableSignaturePreference")?.apply {
            setOnPreferenceChangeListener { _, newValue ->
                showSnackbar(getString(R.string.signature_enabled))
                setSignaturePreference?.isEnabled = newValue as Boolean
                true
            }
        }

        setSignaturePreference?.apply {
            setOnPreferenceChangeListener { _, newValue ->
                showSnackbar(newValue.toString())
                true
            }
        }
    }
}
