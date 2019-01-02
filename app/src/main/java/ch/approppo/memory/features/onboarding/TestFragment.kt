package ch.approppo.memory.features.onboarding

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import ch.approppo.memory.R
import ch.approppo.memory.data.api.MemoryAPI
import ch.approppo.memory.entities.Score
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


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
        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(context!!.getString(R.string.base_url))
            .build()
        api = retrofit.create(MemoryAPI::class.java)
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
        api.getScores().enqueue(object : Callback<List<Score>> {
            override fun onFailure(call: Call<List<Score>>, t: Throwable) {
                Log.e("TestFragment", "Request failure", t)
            }

            override fun onResponse(call: Call<List<Score>>, response: Response<List<Score>>) {
                if (response.isSuccessful) {
                    tvOutput.text = response.body()?.joinToString() ?: "Kann body nicht lesen"
                } else {
                    tvOutput.text = response.errorBody()?.string() ?: "Kann error-body nicht lesen"
                }
            }
        })

    }
}

//class AsyncExecutor(
//    private val api: MemoryAPI,
//    private val callback: ((String) -> Unit)
//) : AsyncTask<Void, Void, String>() {
//
//    override fun doInBackground(vararg params: Void?): String {
//        return api.getScores()
//    }
//
//    override fun onPostExecute(result: String?) {
//        callback(result ?: "")
//    }
//}