package ch.approppo.memory.features.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ch.approppo.memory.R

class LoginActivity : AppCompatActivity() {

    companion object {
        fun newIntent(ctx: Context) = Intent(ctx, LoginActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_login)
    }
}
