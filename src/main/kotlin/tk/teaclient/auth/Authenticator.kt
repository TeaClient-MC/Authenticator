/*
    TeaClient Authenticator
    Copyright (C) 2023  TeaClient

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/package tk.teaclient.auth

import org.apache.logging.log4j.LogManager
import tk.teaclient.auth.server.LoginServer
import tk.teaclient.auth.service.SessionChangerProvider
import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * The [LogManager] logger object used to log messages to the console.
 */
val logger = LogManager.getLogger()!!

/**
 * The `Authenticator` class provides the ability to log in to a Microsoft account and obtain a Minecraft session.
 *
 * @param clientID The Application Client ID, which should provide a localhost Redirect URI.
 * @param changeSession If true, changes the Minecraft session using a `SessionChangerProvider` object.
 * @param logging If true, logs messages to the console.
 */
class Authenticator(
    private val clientID: String,
    private val changeSession: Boolean = false,
    val logging: Boolean = false
) {
    /**
     * The [ServiceLoader] object used to load a [SessionChangerProvider] object, which is used to change the Minecraft session.
     */
    private val serviceLoader = ServiceLoader.load(SessionChangerProvider::class.java)

    /**
     * The [SessionChangerProvider] object, loaded using the [serviceLoader] object.
     */
    private val sessionChangerProvider = serviceLoader.findFirst().orElse(null)


    /**
     * Starts the login server on a randomly generated port, and returns a [CompletableFuture] that will contain
     * the [MinecraftSessionResult] when the login is complete.
     *
     * @return A [CompletableFuture] that will contain the [MinecraftSessionResult] when the login is complete.
     */
    fun login(): CompletableFuture<MinecraftSessionResult> {
        return CompletableFuture<MinecraftSessionResult>().completeAsync(this::startServer)
    }

    /**
     * Starts the login server and returns a dummy [MinecraftSessionResult] object.
     *
     * @return A dummy [MinecraftSessionResult] object.
     */
    private fun startServer(): MinecraftSessionResult {
        // Start the login server.
        val server = LoginServer(clientID)
        server.handle(this::onLogin)
        server.start()

        // Return a dummy MinecraftSessionResult object to satisfy the return type of the CompletableFuture.
        return MinecraftSessionResult("", UUID.randomUUID(), "")
    }

    /**
     * Parses the authentication code from the Microsoft account login request.
     *
     * @param req The login request as a string.
     * @return The authentication code as a string.
     */
    private fun getCode(req: String): String {
        // The login request is in the format "GET /?code=<auth_code> HTTP/1.1", so we split it by "=" and return the second part.
        return req.split("=")[1].split(" ")[0]
    }

    /**
     * Handles the login request from the Microsoft account.
     *
     * @param req The login request as a string.
     * @return The response HTML as a string.
     */
    private fun onLogin(req: String): String {
        // Parse the authentication code from the login request.
        val code = getCode(req)

        // TODO: Implement actual login logic here.

        // Return a welcome message to the user.
        return "<p>Welcome!</p>"
    }
}
