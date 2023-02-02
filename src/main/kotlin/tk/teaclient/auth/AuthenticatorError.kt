package tk.teaclient.auth

class AuthenticatorError(override val message: String): RuntimeException(message)