package ch.approppo.memory

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Html
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class AGBFragment : Fragment() {

    companion object {
        fun newFragment(): Fragment = AGBFragment()
    }

    private lateinit var callback: OnboardingFlowCallback

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callback = context as? OnboardingFlowCallback ?: throw IllegalStateException("Host must implement OnboardingFlowCallback")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        val view = inflater.inflate(R.layout.fragment_agb, container, false)
        val agb = resources.openRawResource(R.raw.agb).bufferedReader().use {
            it.readText()
        }
        view.findViewById<TextView>(R.id.tv_agb).text = Html.fromHtml(agb)
        return view
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.onboarding_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.next) {
            callback.nextFromAGB()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}
