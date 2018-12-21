package ch.approppo.memory.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import ch.approppo.memory.entities.History


/**
 *
 *
 * @author Martin Neff, approppo GmbH, martin.neff@approppo.ch
 * created on 21.12.18.
 */
@Database(
    entities = [
        History::class
    ], version = 1
)
abstract class MemoryDB : RoomDatabase() {

    abstract fun historyDao(): HistoryDao
}