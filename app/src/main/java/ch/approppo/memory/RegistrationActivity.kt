package ch.approppo.memory

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_onboarding_registration.et_password
import kotlinx.android.synthetic.main.activity_onboarding_registration.et_password_confirmation
import kotlinx.android.synthetic.main.activity_onboarding_registration.et_username

class RegistrationActivity : AppCompatActivity() {

    companion object {
        const val KEY_TOKEN = "KEY_TOKEN"
        fun newIntent(ctx: Context) = Intent(ctx, RegistrationActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_registration)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.onboarding_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.next) {
            if (validateInput()) {
                PreferenceManager.getDefaultSharedPreferences(this).edit().putString(KEY_TOKEN, "my token").apply()
                startActivity(AGBActivity.newIntent(this))
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun validateInput(): Boolean {
        var isAllValid = true
        if (et_username.text.toString().trim().isBlank()) {
            et_username.error = "Username darf nicht leer sein!"
            isAllValid = false
        }

        if (et_password.text.toString().trim().isBlank()) {
            et_password.error = "Passwort darf nicht leer sein!"
            isAllValid = false
        }

        if (et_password_confirmation.text.toString().trim().isBlank()) {
            et_password_confirmation.error = "Passwortbestätigung darf nicht leer sein!"
            isAllValid = false
        }

        if (et_password_confirmation.text.toString().trim() != et_password.text.toString().trim()) {
            et_password_confirmation.error = "Passwortbestätigung stimmt nicht mit Passwort überein!"
            isAllValid = false
        }

        return isAllValid
    }
}
