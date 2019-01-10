package ch.approppo.memory.features.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import ch.approppo.memory.R


class MainActivity : AppCompatActivity() {

    companion object {
        fun newIntent(ctx: Context) = Intent(ctx, MainActivity::class.java)
    }

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener {

            if (!it.isChecked) {
                it.isChecked = true
                when (it.itemId) {
                    R.id.nav_action_game -> replaceFragment(GameBoardFragment.newFragment())
                    R.id.nav_action_history -> replaceFragment(HistoryFragment.newFragment())
                    R.id.nav_action_ranking -> replaceFragment(ScoresFragment.newFragment())
                    R.id.nav_action_profile -> {

                    }
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        val actionbar = supportActionBar!!
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, GameBoardFragment.newFragment())
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    drawerLayout.openDrawer(GravityCompat.START)
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun replaceFragment(newFragment: Fragment) {
        val fragment = supportFragmentManager.findFragmentById(R.id.main_container)
        if (fragment != null && fragment::class != newFragment::class) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, newFragment)
                .commit()
        }
    }
}