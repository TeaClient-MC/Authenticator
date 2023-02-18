package tk.teaclient.auth

/**
 * Exception class representing an error that occurred during authentication.
 *
 * @property message A description of the error that occurred.
 */
class AuthenticatorError(override val message: String): RuntimeException(message)
