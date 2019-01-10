package ch.approppo.memory.features.home

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import ch.approppo.memory.MemoryApp
import ch.approppo.memory.R
import ch.approppo.memory.data.ScoreRepository
import ch.approppo.memory.entities.Score
import ch.approppo.memory.entities.ScoreEvent
import com.squareup.picasso.Picasso
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 *
 *
 * @author Martin Neff, approppo GmbH, martin.neff@approppo.ch
 * created on 10.01.19.
 */
class ScoresFragment : Fragment() {

    companion object {
        fun newFragment(): Fragment = ScoresFragment()
    }

    private lateinit var adapter: ScoresAdapter

    private lateinit var scoreRepository: ScoreRepository

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        scoreRepository = (context?.applicationContext as? MemoryApp)?.getScoreRepository() ?: throw IllegalStateException("Unable to find ScoreRepositry")
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        val listView: ListView = view.findViewById(R.id.list)
        adapter = ScoresAdapter(requireContext(), R.layout.score_listitem_layout, mutableListOf())
        listView.adapter = adapter
        return view
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this)
        scoreRepository.getScores()
    }

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun handleScoresEvent(scoreEvent: ScoreEvent) {
        if (scoreEvent.scores != null) {
            adapter.clear()
            adapter.addAll(scoreEvent.scores)
            adapter.notifyDataSetChanged()
        } else {
            val t = scoreEvent.t ?: RuntimeException("Da ging was schief")
            Log.e("ScoresFragment", t.message, t)
        }
    }

    class ScoresAdapter(
        ctx: Context,
        private val resId: Int,
        list: MutableList<Score>
    ) : ArrayAdapter<Score>(ctx, resId, list) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

            val view = convertView ?: LayoutInflater.from(context).inflate(resId, parent, false)
            val score = getItem(position)!!
            val imageView = view.findViewById<ImageView>(R.id.iv_profile)
            Picasso.get().load(context.getString(R.string.base_url) + "/user/${score.userId}/avatar").placeholder(R.drawable.ic_ranking).into(imageView)
            view.findViewById<TextView>(R.id.tv_score_value).text = score.score.toString()
            return view
        }
    }
}