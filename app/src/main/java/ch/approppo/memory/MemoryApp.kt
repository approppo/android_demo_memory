package ch.approppo.memory

import android.app.Application
import android.arch.persistence.room.Room
import ch.approppo.memory.data.HistoryRepository
import ch.approppo.memory.data.ScoreRepository
import ch.approppo.memory.data.UserRepository
import ch.approppo.memory.data.api.MemoryAPI
import ch.approppo.memory.data.db.MemoryDB
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 *
 *
 * @author Martin Neff, approppo GmbH, martin.neff@approppo.ch
 * created on 17.12.18.
 */
class MemoryApp : Application() {


    private lateinit var historyRepository: HistoryRepository
    private lateinit var userRepository: UserRepository
    private lateinit var scoreRepository: ScoreRepository

    override fun onCreate() {
        super.onCreate()
        val db = Room
            .databaseBuilder(applicationContext, MemoryDB::class.java, "memory-db")
            .allowMainThreadQueries()
            .build()
        historyRepository = HistoryRepository(db)

        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(getString(R.string.base_url))
            .build()
        val memoryAPI = retrofit.create(MemoryAPI::class.java)
        userRepository = UserRepository(memoryAPI, this)
        scoreRepository = ScoreRepository(memoryAPI)

    }

    fun getHistoryRepostory() = historyRepository
    fun getUserRepository() = userRepository
    fun getScoreRepository() = scoreRepository
}