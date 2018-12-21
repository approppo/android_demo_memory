package ch.approppo.memory

import android.app.Application
import android.arch.persistence.room.Room
import ch.approppo.memory.data.HistoryRepository
import ch.approppo.memory.data.db.MemoryDB

/**
 *
 *
 * @author Martin Neff, approppo GmbH, martin.neff@approppo.ch
 * created on 17.12.18.
 */
class MemoryApp : Application() {


    private lateinit var historyRepository: HistoryRepository

    override fun onCreate() {
        super.onCreate()
        val db = Room
            .databaseBuilder(applicationContext, MemoryDB::class.java, "memory-db")
            .allowMainThreadQueries()
            .build()
        historyRepository = HistoryRepository(db)
    }

    fun getHistoryRepostory() = historyRepository
}