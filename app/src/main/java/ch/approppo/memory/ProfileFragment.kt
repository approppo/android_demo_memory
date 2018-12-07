package ch.approppo.memory

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup

class ProfileFragment : Fragment() {

    companion object {
        fun newFragment(): Fragment = ProfileFragment()
    }

    private lateinit var callback: OnboardingFlowCallback

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callback = context as? OnboardingFlowCallback ?:
                throw IllegalStateException("Host must implement OnboardingFlowCallback")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.onboarding_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.next) {
            callback.nextFromProfile()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
