package ch.approppo.memory.features.onboarding


/**
 *
 *
 * @author Martin Neff, approppo GmbH, martin.neff@approppo.ch
 * created on 07.12.18.
 */
interface OnboardingFlowCallback {

    fun startRegistration()

    fun startLogin()

    fun nextFromRegistration()

    fun nextFromAGB()

    fun nextFromProfile()
}