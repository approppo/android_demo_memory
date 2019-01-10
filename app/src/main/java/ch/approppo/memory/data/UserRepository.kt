package ch.approppo.memory.data

import android.content.Context
import android.preference.PreferenceManager
import ch.approppo.memory.data.api.MemoryAPI
import ch.approppo.memory.entities.APIException
import ch.approppo.memory.entities.RegistrationEvent
import ch.approppo.memory.entities.RegistrationResponse
import ch.approppo.memory.entities.User
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
class UserRepository(
    private val memoryAPI: MemoryAPI,
    context: Context
) {

    private val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)

    companion object {
        private const val USER_ID_KEY = "user.id.key"
    }

    fun isLoggedIn() = sharedPrefs.contains(USER_ID_KEY)

    fun registerUser(user: User) {
        val call = memoryAPI.register(user)
        call.enqueue(object : Callback<RegistrationResponse> {
            override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
                EventBus.getDefault().post(RegistrationEvent(t))
            }

            override fun onResponse(call: Call<RegistrationResponse>, response: Response<RegistrationResponse>) {
                if (response.isSuccessful) {
                    sharedPrefs.edit().putLong(USER_ID_KEY, response.body()!!.userId).apply()
                    EventBus.getDefault().post(RegistrationEvent())
                } else {
                    EventBus.getDefault().post(RegistrationEvent(APIException("Fehler bei der Registration", null)))
                }
            }
        })
    }
}