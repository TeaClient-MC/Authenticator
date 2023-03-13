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
*/
@file:Suppress("MemberVisibilityCanBePrivate")

package dev.teaclient.auth

import dev.teaclient.auth.HTTPUtils.get
import dev.teaclient.auth.HTTPUtils.post
import dev.teaclient.auth.jsonobjects.req.*
import dev.teaclient.auth.jsonobjects.req.Properties
import dev.teaclient.auth.jsonobjects.res.*
import dev.teaclient.auth.server.LoginServer
import dev.teaclient.auth.service.SessionChangerProvider
import org.apache.logging.log4j.LogManager
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
 * @param clientSecret The Application Client Secret.
 * @param changeSession If true, changes the Minecraft session using a `SessionChangerProvider` object.
 */
class Authenticator(
    private val clientID: String,
    private val clientSecret: String,
    private val changeSession: Boolean = true
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
     * The `LoginServer` instance used to handle the authentication process.
     */
    private lateinit var server: LoginServer

    /**
     * Starts the login server on a randomly generated port, and returns a [CompletableFuture] that will contain
     * the [MinecraftSessionResult] when the login is complete.
     *
     * @return A [CompletableFuture] that will contain the [MinecraftSessionResult] when the login is complete.
     */
    fun login(): CompletableFuture<MinecraftSessionResult> {
        return CompletableFuture.supplyAsync {
            try {
                startServer()
            } catch (e: Exception) {
                logger.error("Error occurred while logging in", e)
                throw AuthenticationError(e)
            }
        }
    }


    /**
     * Starts the login server and returns a dummy [MinecraftSessionResult] object.
     *
     * This function creates a new [LoginServer] instance with the specified [clientID],
     * registers a callback function to handle login requests, and starts the server.
     *
     * @return A [MinecraftSessionResult] object representing the result of the login request.
     */
    private fun startServer(): MinecraftSessionResult {
        // Create a new LoginServer instance with the specified client ID.
        this.server = LoginServer(clientID)

        // Register a callback function to handle login requests.
        server.handle(this::onLogin)

        // Start the server and return the result of the login request.
        return server.start()
    }


    /**
     * Parses the authentication code from the Microsoft account login request.
     *
     * @param req The login request as a string.
     * @return The authentication code as a string.
     */
    private fun getCode(req: String): String {
        val parts = req.split("=".toRegex(), 2)
        if (parts[0] == "error") {
            val description = req.split("error_description=".toRegex())[1].replace("+", " ")
            throw AuthenticationError(description)
        }
        return parts[1].split(" ")[0]
    }


    /**
     * Handles the login request from the Microsoft account by first obtaining a Microsoft access token, then using it to get
     * an Xbox Live and Xbox Secure Token Service (XSTS) token, and finally using the XSTS token to get a Minecraft access token.
     *
     * @param req The login request as a string.
     * @return A [MinecraftSessionResult] object containing the user's Minecraft profile information, Microsoft access token,
     *         and refresh token.
     */
    private fun onLogin(req: String): MinecraftSessionResult {
        // Parse the authentication code from the login request.
        val code = getCode(req)

        // Set the parameters for the Microsoft access token request.
        val params = mutableMapOf(
            "client_id" to clientID,
            "scope" to "XboxLive.signin offline_access",
            "code" to code,
            "redirect_uri" to "http://localhost:${server.port}",
            "grant_type" to "authorization_code",
            "client_secret" to clientSecret
        )

        return loginCommon(params)
    }

    /**
     * Refreshes the Minecraft session using the given refresh token.
     *
     * @param token The refresh token.
     * @return A [MinecraftSessionResult] object containing the user's Minecraft profile information, Microsoft access token,
     *         and refresh token.
     */
    fun refresh(token: String) = run {
        CompletableFuture.supplyAsync {
            try {
                val params = mutableMapOf(
                    "client_id" to clientID,
                    "scope" to "XboxLive.signin offline_access",
                    "refresh_token" to token,
                    "redirect_uri" to "http://localhost:${server.port}",
                    "grant_type" to "refresh_token",
                    "client_secret" to clientSecret
                )

                loginCommon(params)
            } catch (e: Exception) {
                logger.error("Error occurred while refreshing token", e)
                throw AuthenticationError(e)
            }
        }
    }

    fun refresh(minecraftSessionResult: MinecraftSessionResult) = refresh(minecraftSessionResult.refreshToken)

    /**
     * Splits the given UUID into its individual segments by adding hyphens at the appropriate positions.
     * If the UUID is already formatted with hyphens, it will be returned as-is.
     *
     * @param uuid The UUID to split.
     * @return The split UUID as a [UUID] object.
     * @throws IllegalArgumentException If the UUID is not a valid format.
     */
    private fun splitUUID(uuid: String): UUID {
        val hyphenCount = uuid.count { it == '-' }
        val formattedUuid = when (hyphenCount) {
            0 -> uuid.substring(0, 8) + "-" + uuid.substring(8, 12) + "-" + uuid.substring(12, 16) + "-" +
                    uuid.substring(16, 20) + "-" + uuid.substring(20)

            4 -> uuid // UUID is already formatted with hyphens
            else -> throw IllegalArgumentException("Invalid UUID format")
        }
        return UUID.fromString(formattedUuid)
    }

    /**
     * Performs the common steps in the login process, namely obtaining a Microsoft access token, using it to get an Xbox Live
     * and Xbox Secure Token Service (XSTS) token, and using the XSTS token to get a Minecraft access token.
     *
     * @param params The parameters for the Microsoft access token request.
     * @return A [MinecraftSessionResult] object containing the user's Minecraft profile information, Microsoft access token,
     *         and refresh token.
     */
    private fun loginCommon(params: MutableMap<String, String>): MinecraftSessionResult {
        // Get a Microsoft access token.
        val microsoftAccessToken: MicrosoftAccessTokenRes? = post(
            "https://login.microsoftonline.com/consumers/oauth2/v2.0/token", params
        )

        // Get an Xbox Live and Xbox Secure Token Service (XSTS) token.
        val xblRes: XBLandXSTSRes? = post(
            "https://user.auth.xboxlive.com/user/authenticate",
            XBLReq(Properties(rpsTicket = "d=${microsoftAccessToken?.accessToken}"))
        )

        // Use the XSTS token to get a Minecraft access token.
        val xstsRes: XBLandXSTSRes? = post(
            "https://xsts.auth.xboxlive.com/xsts/authorize",
            XSTSReq(XSTSProperties(userTokens = listOf(xblRes!!.token)))
        )

        val minecraftRes: MinecraftRes? = post(
            "https://api.minecraftservices.com/authentication/login_with_xbox",
            MinecraftReq(identityToken = "XBL3.0 x=${xstsRes?.displayClaims?.xui?.get(0)?.uhs};${xstsRes?.token}")
        )

        // Check for game ownership and log a warning if the item array is empty.
        val ownershipRes: OwnershipRes? = get(
            "https://api.minecraftservices.com/entitlements/mcstore",
            mapOf("Authorization" to "Bearer ${minecraftRes?.accessToken}")
        )

        // Log a warning if the user does not own any Minecraft items.
        if ((ownershipRes?.items ?: emptyList()).isEmpty()) {
            logger.warn("The user does not own any Minecraft items.")
        }

        // Use the Minecraft access token to get the user's Minecraft profile information.
        val minecraftProfileRes: MinecraftProfileRes? = get(
            "https://api.minecraftservices.com/minecraft/profile",
            mapOf("Authorization" to "Bearer ${minecraftRes?.accessToken}")
        )

        return MinecraftSessionResult(
            minecraftProfileRes!!.name,
            splitUUID(minecraftProfileRes.id),
            microsoftAccessToken!!.accessToken,
            microsoftAccessToken.refreshToken
        ).also { if (changeSession) sessionChangerProvider?.changeSession(it) }
    }
}
