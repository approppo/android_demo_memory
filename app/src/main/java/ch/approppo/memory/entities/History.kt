package ch.approppo.memory.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


/**
 *
 *
 * @author Martin Neff, approppo GmbH, martin.neff@approppo.ch
 * created on 21.12.18.
 */
@Entity
data class History(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val userName: String,
    val date: Long,
    val score: Int
)