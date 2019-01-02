package ch.approppo.memory.data.api

import ch.approppo.memory.entities.Score


/**
 *
 *
 * @author Martin Neff, approppo GmbH, martin.neff@approppo.ch
 * created on 02.01.19.
 */
interface MemoryAPI {

    fun getScores(): List<Score>
}