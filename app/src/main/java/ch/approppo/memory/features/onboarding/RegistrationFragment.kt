package ch.approppo.memory.features.onboarding

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import ch.approppo.memory.MemoryApp
import ch.approppo.memory.R
import ch.approppo.memory.data.UserRepository
import ch.approppo.memory.entities.RegistrationEvent
import ch.approppo.memory.entities.User
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class RegistrationFragment : Fragment() {

    companion object {
        const val KEY_TOKEN = "KEY_TOKEN"
        fun newFragment(): Fragment = RegistrationFragment()
    }

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var etPasswordConfirmation: EditText

    private lateinit var userRepository: UserRepository

    private lateinit var callback: OnboardingFlowCallback

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callback = context as? OnboardingFlowCallback ?: throw IllegalStateException("Host must implement OnboardingFlowCallback")

        userRepository = (context.applicationContext as? MemoryApp)?.getUserRepository() ?: throw IllegalStateException("Unable to get UserRepository")
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
                userRepository.registerUser(User(null, etUsername.text.toString().trim(), etPassword.text.toString().trim()))
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this)
    }

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun handleRegistrationEvent(registrationEvent: RegistrationEvent) {
        if (registrationEvent.t == null) {
            callback.nextFromRegistration()
        } else {
            Log.e("RegistrationFragment", registrationEvent.t.message, registrationEvent.t)
        }
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
