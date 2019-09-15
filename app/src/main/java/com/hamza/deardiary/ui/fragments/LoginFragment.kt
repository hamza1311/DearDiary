package com.hamza.deardiary.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.hamza.deardiary.R
import com.hamza.deardiary.util.showSnackbar
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {
    private val prefs by lazy {
        PreferenceManager.getDefaultSharedPreferences(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (prefs.getBoolean("enablePasswordPreference", false) || prefs.getString(
                "setPassword",
                ""
            ) != ""
        ) {
            login_button.setOnClickListener {
                val password = login_password_editText.text.toString()
                if (prefs.getString("setPassword", "") == password && password.isNotBlank()) {
                    findNavController().apply {
                        navigate(LoginFragmentDirections.actionLoginFragmentToMainFragment())
                    }
                } else {
                    showSnackbar(getString(R.string.incorrect_password))
                }
            }
        } else {
            findNavController().apply {
                navigate(LoginFragmentDirections.actionLoginFragmentToMainFragment())
            }
        }
    }
}
