package ch.approppo.memory.features.home

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import ch.approppo.memory.MemoryApp
import ch.approppo.memory.R
import ch.approppo.memory.data.HistoryRepository
import kotlin.random.Random


/**
 *
 *
 * @author Martin Neff, approppo GmbH, martin.neff@approppo.ch
 * created on 07.12.18.
 */
class GameBoardFragment : Fragment() {

    companion object {
        fun newFragment(): Fragment = GameBoardFragment()
    }

    private lateinit var tvScore: TextView

    private var cards = mutableMapOf<Button, String>()

    private var count = 0

    private var firstCard: Button? = null
    private var secondCard: Button? = null
    private var matchedCards = mutableListOf<Button>()

    private lateinit var historyRepository: HistoryRepository

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        historyRepository = (context?.applicationContext as? MemoryApp)?.getHistoryRepostory() ?: throw IllegalStateException("Cannot retrieve HistoryRepo")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_gameboard, container, false)
        val emojis = mutableListOf("🐵", "🐶", "🐱", "🐻", "🦁", "🐮", "🐨", "🐷", "🐸", "🐔", "🦆", "🦅", "🦉", "🦄", "🐙", "🐘", "🦍", "🦓")

        tvScore = view.findViewById(R.id.tv_score)

        val tmpCards = mutableListOf<Button>()
        findButtons(tmpCards, view.findViewById(R.id.game_board))
        for (i in 0 until tmpCards.size / 2) {
            val emoji = emojis.removeAt(Random.nextInt(emojis.size))

            val button1 = tmpCards.removeAt(Random.nextInt(tmpCards.size))
            button1.setOnClickListener { flipCard(it) }
            cards[button1] = emoji

            val button2 = tmpCards.removeAt(Random.nextInt(tmpCards.size))
            button2.setOnClickListener { flipCard(it) }
            cards[button2] = emoji
        }
        return view
    }

    private fun findButtons(tmpCards: MutableList<Button>, parentLayout: LinearLayout) {
        for (i in 0 until parentLayout.childCount) {
            val child = parentLayout.getChildAt(i)
            if (child is Button) {
                tmpCards.add(child)
            } else if (child is LinearLayout) {
                findButtons(tmpCards, child)
            }
        }
    }

    private fun flipCard(view: View) {
        val button = view as Button
        if (button.text.toString().isBlank()) {
            flipUp(button)

            if (firstCard != null && secondCard != null) {
                flipDown(firstCard!!)
                flipDown(secondCard!!)
                firstCard = null
                secondCard = null
            }

            if (firstCard == null) {
                firstCard = button
            } else if (secondCard == null) {
                secondCard = button
                validateCards()
            }
        }
    }

    private fun flipDown(button: Button) {
        if (!matchedCards.contains(button)) {
            button.text = ""
            button.setBackgroundColor(resources.getColor(android.R.color.holo_green_dark))
        }
    }

    private fun flipUp(button: Button) {
        button.setBackgroundColor(resources.getColor(android.R.color.darker_gray))
        button.text = cards[button]
        tvScore.text = "Flips: ${++count}"
    }

    private fun validateCards() {
        if (firstCard!!.text == secondCard!!.text) {
            matchedCards.add(firstCard!!)
            matchedCards.add(secondCard!!)

            if(matchedCards.size == cards.size) {
                historyRepository.saveGame(count)
            }
        }
    }
}