package ch.approppo.memory.features.onboarding

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import ch.approppo.memory.MemoryApp
import ch.approppo.memory.R
import ch.approppo.memory.data.UserRepository
import ch.approppo.memory.features.home.MainActivity

class OnboardingActivity : AppCompatActivity(), OnboardingFlowCallback {

    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userRepository = (application as MemoryApp).getUserRepository()
        if (userRepository.isLoggedIn()) {
            startActivity(MainActivity.newIntent(this))
            finish()
            return
        }

        setContentView(R.layout.activity_onboarding)

        if (savedInstanceState == null) {
            replaceFragment(OnboardingFragment.newFragment())
        }
    }

    override fun startRegistration() {
        replaceFragment(RegistrationFragment.newFragment())
    }

    override fun startLogin() {

    }

    override fun nextFromRegistration() {
        replaceFragment(AGBFragment.newFragment())
    }

    override fun nextFromAGB() {
        replaceFragment(ProfileFragment.newFragment())
    }

    override fun nextFromProfile() {
        startActivity(MainActivity.newIntent(this))
        finish()
    }

    private fun replaceFragment(newFragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, newFragment)
            .commit()
    }
}
