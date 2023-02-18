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

package tk.teaclient.auth.server

import tk.teaclient.auth.logger
import tk.teaclient.auth.url
import java.awt.Desktop
import java.net.ServerSocket
import java.net.Socket
import java.net.URL
import kotlin.random.Random

/**
 * Login Server class for Microsoft authentication
 *
 * @property clientID Application ClientID to use for authentication
 * @property port Port number of the login server. A random port is used by default.
 */
class LoginServer(
    private val clientID: String,
    private val port: Int = Random.Default.nextInt(10000)
) {
    /** URL of the login page for Microsoft authentication */
    val url: URL
        get() = ("https://login.microsoftonline.com/consumers/oauth2/v2.0/authorize" +
                "?client_id=$clientID" +
                "&scope=XboxLive.signin" +
                "&redirect_uri=http://localhost:$port" +
                "&response_type=code").url()

    /**
     * The function to handle incoming login requests.
     * The function takes in a request string and returns a response string.
     */
    private lateinit var handleFunction: (String) -> String

    /** The server socket used to listen for incoming login requests. */
    private lateinit var serverSocket: ServerSocket

    /** The socket for the incoming login request. */
    private lateinit var socket: Socket

    /**
     * Sets the function to handle incoming login requests.
     * The function takes in a request string and returns a response string.
     *
     * @param function the function to handle incoming login requests.
     */
    fun handle(function: (String) -> String) {
        this.handleFunction = function
    }

    /**
     * Starts the login server and waits for incoming login requests.
     *
     * @return the current [LoginServer] instance.
     */
    fun start(): LoginServer {
        logger.info("Starting server on $port")
        serverSocket = ServerSocket(port)
        Desktop.getDesktop().browse(url.toURI())
        socket = serverSocket.accept()

        // Waits until a client connects.
        socket.use {
            it.getInputStream().use { inputStream ->
                val line = inputStream.bufferedReader().readLine()
                it.getOutputStream().apply {
                    val os = this
                    var response = ""
                    // Calls the handle function to generate a response.
                    if (this@LoginServer::handleFunction.isInitialized) {
                        response += this@LoginServer.handleFunction.invoke(line)
                    }
                    // Writes the response to the client.
                    os.write(
                        ("HTTP/1.1 200 OK\r\n" +
                                "Content-Type: text/html; charset=UTF-8\r\n" +
                                "Content-Length: ${response.length}\r\n\r\n")
                            .toByteArray()
                    )
                    os.write(response.toByteArray())
                    os.flush()
                    os.close()
                }
            }
        }
        return this
    }
}
