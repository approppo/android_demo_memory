package ch.approppo.memory

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    companion object {
        fun newIntent(ctx: Context) = Intent(ctx, MainActivity::class.java)
    }

    private lateinit var tvScore: TextView

    private var cards = mutableMapOf<Button, String>()

    private var count = 0

    private var firstCard: Button? = null
    private var secondCard: Button? = null
    private var matchedCards = mutableListOf<Button>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val emojis = mutableListOf("🐵", "🐶", "🐱", "🐻", "🦁", "🐮", "🐨", "🐷", "🐸", "🐔", "🦆", "🦅", "🦉", "🦄", "🐙", "🐘", "🦍", "🦓")

        tvScore = findViewById(R.id.tv_score)

        val tmpCards = mutableListOf<Button>()
        findButtons(tmpCards, findViewById(R.id.game_board))
        for (i in 0 until tmpCards.size / 2) {
            val emoji = emojis.removeAt(Random.nextInt(emojis.size))
            cards[tmpCards.removeAt(Random.nextInt(tmpCards.size))] = emoji
            cards[tmpCards.removeAt(Random.nextInt(tmpCards.size))] = emoji
        }
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

    fun flipCard(view: View) {
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
        }
    }
}
