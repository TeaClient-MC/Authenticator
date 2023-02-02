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
package tk.teaclient.auth

import org.apache.logging.log4j.LogManager
import tk.teaclient.auth.server.*
import tk.teaclient.auth.service.*
import java.net.URL
import java.util.*
import java.util.concurrent.CompletableFuture

val logger = LogManager.getLogger()!!


/**
 * Authenticator class, it provides ability to log in to your microsoft account
 * @param clientID Application ClientID, it should provide localhost Redirect URI
 * @param changeSession Changes minecraft session if [SessionChangerProvider] is provided
 * @param logging Logs messages to console
 */
class Authenticator(
    private val clientID: String,
    private val changeSession: Boolean = false,
    val logging: Boolean = false
) {

    private val serviceLoader = ServiceLoader.load(SessionChangerProvider::class.java)
    private val sessionChangerProvider = serviceLoader.findFirst().orElse(null)



    /**
     * Starts login server on port: [LoginServer.port] (Randomly generated till: 10000)
     * @return Result of login
     */
    fun login(): CompletableFuture<MinecraftSessionResult> {
        return CompletableFuture<MinecraftSessionResult>().completeAsync(this::startServer)
    }

    private fun startServer(): MinecraftSessionResult {

        val server = LoginServer(clientID)
        server.handle(this::onLogin)
        server.start()

        // just to return smth
        return MinecraftSessionResult("", UUID.randomUUID(), "")
    }

    private fun onLogin(req: String): String {
        val code = getCode(req)
        println(code)
        return "<p> welcome </p>"
    }

    private fun getCode(req: String): String {
        // ughhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh
        return req.split("=")[1].split(" ")[0]
    }

}