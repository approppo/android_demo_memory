package ch.approppo.memory.entities


/**
 *
 *
 * @author Martin Neff, approppo GmbH, martin.neff@approppo.ch
 * created on 10.01.19.
 */
class ScoreEvent(
    val scores: List<Score>?,
    val t: Throwable? = null
)