package ch.approppo.memory.features.onboarding

import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import ch.approppo.memory.R

class RegistrationFragment : Fragment() {

    companion object {
        const val KEY_TOKEN = "KEY_TOKEN"
        fun newFragment(): Fragment = RegistrationFragment()
    }

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var etPasswordConfirmation: EditText

    private lateinit var callback: OnboardingFlowCallback

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callback = context as? OnboardingFlowCallback ?:
                throw IllegalStateException("Host must implement OnboardingFlowCallback")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        val view = inflater.inflate(R.layout.fragment_registration, container, false)
        etUsername = view.findViewById(R.id.et_username)
        etPassword = view.findViewById(R.id.et_password)
        etPasswordConfirmation = view.findViewById(R.id.et_password_confirmation)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.onboarding_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.next) {
            if (validateInput()) {
                PreferenceManager.getDefaultSharedPreferences(requireContext()).edit().putString(KEY_TOKEN, "my token")
                    .apply()
                callback.nextFromRegistration()
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun validateInput(): Boolean {
        var isAllValid = true
        if (etUsername.text.toString().trim().isBlank()) {
            etUsername.error = "Username darf nicht leer sein!"
            isAllValid = false
        }

        if (etPassword.text.toString().trim().isBlank()) {
            etPassword.error = "Passwort darf nicht leer sein!"
            isAllValid = false
        }

        if (etPasswordConfirmation.text.toString().trim().isBlank()) {
            etPasswordConfirmation.error = "Passwortbestätigung darf nicht leer sein!"
            isAllValid = false
        }

        if (etPasswordConfirmation.text.toString().trim() != etPassword.text.toString().trim()) {
            etPasswordConfirmation.error = "Passwortbestätigung stimmt nicht mit Passwort überein!"
            isAllValid = false
        }

        return isAllValid
    }
}
