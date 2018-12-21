package ch.approppo.memory.features.home

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import ch.approppo.memory.MemoryApp
import ch.approppo.memory.R
import ch.approppo.memory.entities.History
import java.util.*

class HistoryFragment : Fragment() {

    companion object {
        fun newFragment() = HistoryFragment()
    }

    private lateinit var listView: ListView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        listView = view.findViewById(R.id.rv_history_list)

        val list = (requireActivity().application as MemoryApp).getHistoryRepostory().readHistory()
        listView.adapter = HistoryAdapter(requireContext(), R.layout.listitem_layout, list.toMutableList())
        return view
    }

    class HistoryAdapter(
        ctx: Context,
        private val resId: Int,
        list: MutableList<History>
    ) : ArrayAdapter<History>(ctx, resId, list) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

            val view = convertView ?: LayoutInflater.from(context).inflate(resId, parent, false)

            view.findViewById<TextView>(R.id.tv_score_date).text = Date(getItem(position)?.date ?: 0).toString()
            view.findViewById<TextView>(R.id.tv_score_value).text = getItem(position)?.score?.toString()
            return view
        }
    }
}