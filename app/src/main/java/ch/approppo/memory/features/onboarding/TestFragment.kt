package ch.approppo.memory.features.onboarding

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import ch.approppo.memory.R
import ch.approppo.memory.data.api.MemoryAPI
import ch.approppo.memory.data.api.MemoryAPIImpl


/**
 *
 *
 * @author Martin Neff, approppo GmbH, martin.neff@approppo.ch
 * created on 02.01.19.
 */
class TestFragment : Fragment() {

    private lateinit var tvOutput: TextView

    private lateinit var api: MemoryAPI

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        api = MemoryAPIImpl.Companion.Builder(requireContext().applicationContext).build()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.test_layout, container, false)

        tvOutput = view.findViewById(R.id.tv_output)
        view.findViewById<Button>(R.id.bt_test_execute).setOnClickListener {
            getScores()
        }

        return view
    }

    private fun getScores() {
        tvOutput.text = ""
        AsyncExecutor(api) {
            tvOutput.text = it
        }.execute()
    }
}

class AsyncExecutor(
    private val api: MemoryAPI,
    private val callback: ((String) -> Unit)
) : AsyncTask<Void, Void, String>() {

    override fun doInBackground(vararg params: Void?): String {
        return api.getScores()
    }

    override fun onPostExecute(result: String?) {
        callback(result ?: "")
    }
}