package ch.approppo.memory

import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_onboarding.bt_goto_login
import kotlinx.android.synthetic.main.activity_onboarding.bt_goto_registration

class OnboardingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (PreferenceManager.getDefaultSharedPreferences(this).contains(RegistrationActivity.KEY_TOKEN)) {
            startActivity(MainActivity.newIntent(this))
            finish()
            return
        }

        setContentView(R.layout.activity_onboarding)
        bt_goto_login.setOnClickListener { startActivity(LoginActivity.newIntent(this)) }
        bt_goto_registration.setOnClickListener { startActivity(RegistrationActivity.newIntent(this)) }
    }
}
