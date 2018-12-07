package ch.approppo.memory

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class OnboardingFragment : Fragment() {

    private lateinit var callback: OnboardingFlowCallback

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callback = context as? OnboardingFlowCallback ?: throw IllegalStateException("Host must implement OnboardingFlowCallback")
    }

    companion object {
        fun newFragment(): Fragment = OnboardingFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        val view = inflater.inflate(R.layout.fragment_onboarding, container, false)
        view.findViewById<Button>(R.id.bt_goto_login)
            .setOnClickListener { callback.startLogin() }
        view.findViewById<Button>(R.id.bt_goto_registration)
            .setOnClickListener { callback.startRegistration() }
        return view
    }
}
