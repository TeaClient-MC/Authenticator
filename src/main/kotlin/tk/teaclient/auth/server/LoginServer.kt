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
 * Login Server
 * @param port Port of Login Server
 */
class LoginServer(
    private val clientID: String,
    private val port: Int = Random.Default.nextInt(10000)
) {

    val url: URL
        get() = ("https://login.microsoftonline.com/consumers/oauth2/v2.0/authorize" +
                "?client_id=$clientID" +
                "&scope=XboxLive.signin" +
                "&redirect_uri=http://localhost:$port" +
                "&response_type=code").url()

    /**
     * REQ => RES
     */
    private lateinit var handleFunction: (String) -> String


    /* Server stuff */

    private lateinit var serverSocket: ServerSocket
    private lateinit var socket: Socket


    /**
     * Sets handle function,
     * handle function is called when someone connects to the server.
     */
    fun handle(function: (String) -> String) {
        this.handleFunction = function
    }


    /**
     * Starts login server
     */
    fun start(): LoginServer {
        logger.info("Starting server on $port")
        serverSocket = ServerSocket(port)
        Desktop.getDesktop().browse(url.toURI())
        socket = serverSocket.accept()


        // waits till someone connects :)

        socket.use {
            it.getInputStream().use { inputStream ->
                val line = inputStream.bufferedReader().readLine()
                it.getOutputStream().apply {
                    val os = this
                    var response = ""
                    // ughhh
                    if (this@LoginServer::handleFunction.isInitialized) {
                        response += this@LoginServer.handleFunction.invoke(line)
                    }
                    os.write(
                        ("HTML/1.1 200 OK \r\n" +
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
