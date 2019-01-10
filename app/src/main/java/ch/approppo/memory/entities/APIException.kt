package ch.approppo.memory.entities


/**
 *
 *
 * @author Martin Neff, approppo GmbH, martin.neff@approppo.ch
 * created on 10.01.19.
 */
class APIException(message: String? = null, t: Throwable? = null) : RuntimeException(message, t) {
}