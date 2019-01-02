package ch.approppo.memory.data.api

import android.content.Context
import android.util.Log
import ch.approppo.memory.R
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
) {

    companion object {

        private val LOG_TAG = "MemoryAPIImpl"

        class Builder(ctx: Context) {
            val url = ctx.getString(R.string.base_url)

            fun build(): MemoryAPIImpl = MemoryAPIImpl(url)
        }

    }

    fun getScores(): String {

        var con: HttpURLConnection? = null
        try {
            con = URL(url+"/highscore").openConnection() as HttpURLConnection
            con.connect()
            return when (con.responseCode) {
                in 200..299 -> {
                    val response = readStream(con.inputStream)
                    Log.d(LOG_TAG, response)
                    response
                }
                else -> {
                    val response = readStream(con.errorStream)
                    Log.d(LOG_TAG, response)
                    response
                }
            }
        } catch (t: Throwable) {
            Log.e(LOG_TAG, "Connection Failure:\n", t)
        } finally {
            con?.disconnect()
        }
        return "Keine Antwort"
    }

    private fun readStream(stream: InputStream): String {
        return stream.bufferedReader().use {
            it.readText()
        }
    }
}