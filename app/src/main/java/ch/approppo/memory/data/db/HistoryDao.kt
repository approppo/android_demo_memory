package ch.approppo.memory.data.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import ch.approppo.memory.entities.History


/**
 *
 *
 * @author Martin Neff, approppo GmbH, martin.neff@approppo.ch
 * created on 21.12.18.
 */
@Dao
interface HistoryDao {

    @Insert
    fun insertHistory(history: History): Long

    @Query("SELECT * FROM HISTORY")
    fun readAll(): List<History>
}