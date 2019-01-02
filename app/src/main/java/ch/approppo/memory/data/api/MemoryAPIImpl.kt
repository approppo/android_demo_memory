package ch.approppo.memory.data.api

import android.content.Context
import android.util.Log
import ch.approppo.memory.R
import ch.approppo.memory.entities.Score
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


/**
 *
 *
 * @author Martin Neff, approppo GmbH, martin.neff@approppo.ch
 * created on 02.01.19.
 */
class MemoryAPIImpl(
    private val url: String
) : MemoryAPI {

    companion object {

        private val LOG_TAG = "MemoryAPIImpl"

        class Builder(ctx: Context) {
            val url = ctx.getString(R.string.base_url)

            fun build(): MemoryAPI = MemoryAPIImpl(url)
        }

    }

    override fun getScores(): List<Score> {

        var con: HttpURLConnection? = null
        try {
            con = URL(url+"/highscore").openConnection() as HttpURLConnection
            con.connect()
            return when (con.responseCode) {
                in 200..299 -> {
                    Log.d(LOG_TAG, readStream(con.inputStream))
                    emptyList()
                }
                else -> {
                    Log.d(LOG_TAG, readStream(con.errorStream))
                    emptyList()
                }
            }
        } catch (t: Throwable) {
            Log.e(LOG_TAG, "Connection Failure:\n", t)
        } finally {
            con?.disconnect()
        }
        return emptyList()
    }

    private fun readStream(stream: InputStream): String {
        return stream.bufferedReader().use {
            it.readText()
        }
    }
}