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

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL

/*
 * A collection of extension functions for working with input streams and URLs related to authentication.
 */

/**
 * Reads the contents of the input stream and returns it as a string.
 *
 * @return The contents of the input stream as a string.
 */
fun InputStream.string(): String {
    val bufferedReader = BufferedReader(InputStreamReader(this))
    val stringBuilder = StringBuilder()
    var line: String? = bufferedReader.readLine()
    while (line != null) {
        stringBuilder.append(line)
        line = bufferedReader.readLine()
    }
    return stringBuilder.toString()
}

/**
 * Converts this string to a URL object.
 *
 * @return A URL object that represents this string.
 */
inline fun String.url(): URL {
    return URL(this)
}

