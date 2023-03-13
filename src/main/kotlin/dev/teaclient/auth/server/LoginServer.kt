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
package dev.teaclient.auth.server

import dev.teaclient.auth.MinecraftSessionResult
import dev.teaclient.auth.logger
import dev.teaclient.auth.url
import java.awt.Desktop
import java.net.ServerSocket
import java.net.URL
import kotlin.random.Random

/**
 * A server used to handle authentication flow for Minecraft authentication using Xbox Live.
 *
 * @property clientID The client ID to use for authentication flow.
 * @property port The port number to use for the server. Defaults to a random port.
 */
class LoginServer(private val clientID: String, internal val port: Int = Random.Default.nextInt(10000)) {

    /**
     * The URL to use for initiating the authentication flow.
     */
    val url: URL
        get() = ("https://login.microsoftonline.com/consumers/oauth2/v2.0/authorize?client_id=$clientID&scope=XboxLive.signin%20offline_access&redirect_uri=http://localhost:$port&response_type=code").url()

    private lateinit var handleFunction: (String) -> MinecraftSessionResult

    /**
     * Sets a handler function to be called when an authentication code is received.
     *
     * @param handler The function to handle authentication codes.
     */
    fun handle(handler: (String) -> MinecraftSessionResult) {
        this.handleFunction = handler
    }

    /**
     * Starts the server and initiates the authentication flow.
     */
    fun start() = run {
        logger.info("Starting server on $port")
        val serverSocket = ServerSocket(port)
        if(Desktop.isDesktopSupported())
            Desktop.getDesktop().browse(url.toURI())
        lateinit var result: MinecraftSessionResult
        while (true) {
            val socket = serverSocket.accept()

            try {
                socket.getInputStream().use { inputStream ->
                    // read the first line of the HTTP request to get the authentication code
                    val line = inputStream.bufferedReader().readLine()
                    result = handleFunction.invoke(line)
                    socket.getOutputStream().use { outputStream ->
                        // handle the authentication code and generate a response HTML

                        val response = """
                                <html>
                                    <body style="background-color: rgb(14,14,14); color: white;">
                                        <style>
                                            @import url('https://fonts.googleapis.com/css2?family=JetBrains+Mono&display=swap');
                                            body {
                                                user-select: none;
                                            }
                                        </style> 
                                        <div style="display: flex; height: 100%; justify-content: center; align-items: center; font-family: 'JetBrains Mono', monospace;">
                                            <div style="background-color: rgb(10,10,10); display: flex; justify-content: center; align-items: center;">
                                                <img style="padding:15px;"src="https://minotar.net/helm/${result.username}/100.png" width="30px" height="30px"/>
                                                <p style="padding-right:15px;"> Welcome ${result.username}! </p>
                                            </div>
                                        </div>
                                    </body>
                                </html>
                            """.trimIndent()

                        // send the response to the client
                        outputStream.write(
                            ("HTTP/1.1 200 OK\r\n" + "Content-Type: text/html; charset=UTF-8\r\n" + "Content-Length: ${response.length}\r\n\r\n").toByteArray()
                        )
                        outputStream.write(response.toByteArray())
                        outputStream.flush()
                        serverSocket.close()
                    }
                }
            } catch (e: Exception) {
                logger.error("Error processing incoming request: ${e.message}")
            }
        }
        result
    }
}
