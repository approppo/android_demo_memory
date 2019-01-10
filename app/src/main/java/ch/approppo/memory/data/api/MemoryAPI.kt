package ch.approppo.memory.data.api

import ch.approppo.memory.entities.RegistrationResponse
import ch.approppo.memory.entities.Score
import ch.approppo.memory.entities.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


/**
 *
 *
 * @author Martin Neff, approppo GmbH, martin.neff@approppo.ch
 * created on 02.01.19.
 */
interface MemoryAPI {

    @GET("highscore")
    fun getScores(): Call<List<Score>>

    @POST("register")
    fun register(@Body user: User): Call<RegistrationResponse>

    @POST("user/{id}/avatar")
    fun postAvatar(@Path("id") id: Long, @Body avatar: String)
}