package ch.approppo.memory

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.util.*

class HistoryFragment : Fragment() {

    companion object {
        fun newFragment() = HistoryFragment()
    }

    private lateinit var recyclerView: RecyclerView

    val list = mutableListOf(
        History(Date(), 1),
        History(Date(), 2),
        History(Date(), 3),
        History(Date(), 4),
        History(Date(), 5),
        History(Date(), 6),
        History(Date(), 7),
        History(Date(), 8),
        History(Date(), 9),
        History(Date(), 10),
        History(Date(), 11),
        History(Date(), 12),
        History(Date(), 13),
        History(Date(), 14),
        History(Date(), 15),
        History(Date(), 16),
        History(Date(), 17),
        History(Date(), 18),
        History(Date(), 19),
        History(Date(), 20)
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        recyclerView = view.findViewById(R.id.rv_history_list)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val historyAdapter = HistoryAdapter(list, requireContext(), object : OnListItemClickListener<History> {
            override fun onItemClicked(item: History) {
                Log.d("RecyclerView", "Item selected: $item")
            }
        })
        recyclerView.adapter = historyAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        return view
    }

    class HistoryAdapter(
        private val list: MutableList<History>,
        private val context: Context,
        private val listener: OnListItemClickListener<History>
    ) : RecyclerView.Adapter<HistoryView>() {
        private val deleteListener = object : OnItemDeleteListener {
            override fun deleteItemAt(index: Int) {
                list.removeAt(index)
                notifyItemRemoved(index)
            }
        }

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): HistoryView {
            val layout = LayoutInflater.from(context).inflate(R.layout.listitem_layout, p0, false)
            return HistoryView(layout, listener, deleteListener)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(p0: HistoryView, p1: Int) {
            p0.bind(list[p1])
        }

    }

    data class History(
        val date: Date,
        val score: Int
    )

    class HistoryView(
        view: View,
        private val listener: OnListItemClickListener<History>,
        private val deleteListener: OnItemDeleteListener
    ) :
        RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {
        private val date: TextView = view.findViewById(R.id.tv_score_date)
        private val score: TextView = view.findViewById(R.id.tv_score_value)

        fun bind(history: History) {
            itemView.setOnClickListener {
                listener.onItemClicked(history)
            }
            date.text = history.date.toString()
            score.text = history.score.toString()
            itemView.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            menu?.add("Delete")?.setOnMenuItemClickListener { deleteListener.deleteItemAt(adapterPosition);true }
        }
    }

    interface OnListItemClickListener<T> {
        fun onItemClicked(item: T)
    }

    interface OnItemDeleteListener {
        fun deleteItemAt(index: Int)
    }
}