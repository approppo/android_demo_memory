package ch.approppo.memory.data.api

import ch.approppo.memory.entities.Score
import retrofit2.Call
import retrofit2.http.GET


/**
 *
 *
 * @author Martin Neff, approppo GmbH, martin.neff@approppo.ch
 * created on 02.01.19.
 */
interface MemoryAPI {

    @GET("highscore")
    fun getScores(): Call<List<Score>>
}