package com.hamza.deardiary.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.DropDownPreference
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.hamza.deardiary.R
import com.hamza.deardiary.util.showSnackbar
import com.hamza.deardiary.util.toTheme
import java.util.*

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_xml, rootKey)

        val setPasswordPref = findPreference<EditTextPreference>("setPassword")

        findPreference<SwitchPreferenceCompat>("enablePasswordPreference")?.apply {
            setOnPreferenceChangeListener { _, newValue ->
                showSnackbar(getString(if (newValue as Boolean) R.string.password_enabled else R.string.password_disabled))
                setPasswordPref?.isEnabled = newValue
                true
            }
        }

        setPasswordPref?.apply {
            setOnPreferenceChangeListener { _, _ ->
                showSnackbar(getString(R.string.password_set))
                true
            }
        }

        val setSignaturePreference = findPreference<EditTextPreference>("setSignature")

        findPreference<SwitchPreferenceCompat>("enableSignaturePreference")?.apply {
            setOnPreferenceChangeListener { _, newValue ->
                showSnackbar(getString(if (newValue as Boolean) R.string.signature_enabled else R.string.signature_disabled))
                setSignaturePreference?.isEnabled = newValue
                true
            }
        }

        setSignaturePreference?.apply {
            setOnPreferenceChangeListener { _, _ ->
                showSnackbar(getString(R.string.signature_set))
                true
            }
        }

        findPreference<DropDownPreference>("appTheme")?.apply {
            setOnPreferenceChangeListener { preference, newValue ->
                Log.d("ddd", "new val $newValue")
                AppCompatDelegate.setDefaultNightMode(newValue.toString().toLowerCase(Locale.ROOT).toTheme())
                true
            }
        }
    }
}
