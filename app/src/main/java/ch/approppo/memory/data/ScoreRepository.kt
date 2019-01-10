package ch.approppo.memory.data

import ch.approppo.memory.data.api.MemoryAPI
import ch.approppo.memory.entities.APIException
import ch.approppo.memory.entities.Score
import ch.approppo.memory.entities.ScoreEvent
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 *
 *
 * @author Martin Neff, approppo GmbH, martin.neff@approppo.ch
 * created on 10.01.19.
 */
class ScoreRepository(
    private val memoryAPI: MemoryAPI
) {

    fun getScores() {
        val call = memoryAPI.getScores()
        call.enqueue(object : Callback<List<Score>> {
            override fun onFailure(call: Call<List<Score>>, t: Throwable) {
                EventBus.getDefault().post(ScoreEvent(null, t))
            }

            override fun onResponse(call: Call<List<Score>>, response: Response<List<Score>>) {
                if (response.isSuccessful) {
                    EventBus.getDefault().post(ScoreEvent(response.body()!!))
                } else {
                    EventBus.getDefault().post(ScoreEvent(null, APIException("Fehler beim Laden der Scores", null)))
                }
            }
        })
    }
}