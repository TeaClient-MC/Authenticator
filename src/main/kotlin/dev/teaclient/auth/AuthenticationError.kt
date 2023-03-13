package dev.teaclient.auth

/**
 * Exception class representing an error that occurred during authentication.
 *
 * @property message A description of the error that occurred.
 * @property cause The cause of the error.
 */
class AuthenticationError(
    override val message: String,
    override val cause: Throwable? = null
) : RuntimeException(message, cause) {

    /**
     * Constructs a new `AuthenticatorError` with the specified message.
     *
     * @param message A description of the error that occurred.
     */
    constructor(message: String) : this(message, null)

    /**
     * Constructs a new `AuthenticatorError` with the specified cause.
     *
     * @param cause The cause of the error.
     */
    constructor(cause: Throwable) : this(cause.message ?: "", cause)
}
