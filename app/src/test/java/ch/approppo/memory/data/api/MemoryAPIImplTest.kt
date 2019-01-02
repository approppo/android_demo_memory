package ch.approppo.memory.data.api

import org.junit.Before
import org.junit.Test

/**
 * @author Martin Neff, approppo GmbH, martin.neff@approppo.ch
 * created on 02.01.19.
 */
class MemoryAPIImplTest {

    lateinit var sut: MemoryAPIImpl

    @Before
    fun setUp() {
        sut = MemoryAPIImpl("http://167.99.143.163:8080/")
    }


    @Test
    fun getScores() {
        sut.getScores()
    }
}