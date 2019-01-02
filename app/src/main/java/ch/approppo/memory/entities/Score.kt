package ch.approppo.memory.entities


/**
 *
 *
 * @author Martin Neff, approppo GmbH, martin.neff@approppo.ch
 * created on 02.01.19.
 */
data class Score(
    val id: Long,
    val userId: Long,
    val score: Int
)