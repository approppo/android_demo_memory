package ch.approppo.memory.data

import ch.approppo.memory.data.db.MemoryDB
import ch.approppo.memory.entities.History
import java.util.*


/**
 *
 *
 * @author Martin Neff, approppo GmbH, martin.neff@approppo.ch
 * created on 17.12.18.
 */

class HistoryRepository(private val memoryDB: MemoryDB) {

    fun saveGame(score: Int) {
        memoryDB.historyDao().insertHistory(History(null, "tinel", Date().time, score))
    }

    fun readHistory(): List<History> {
        return memoryDB.historyDao().readAll()
    }

}