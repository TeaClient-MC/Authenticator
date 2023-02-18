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
package tk.teaclient.auth.service

import tk.teaclient.auth.MinecraftSessionResult

/**
 * Defines an interface for classes that provide a way to change the current session
 * for a Minecraft account.
 */
interface SessionChangerProvider {

    /**
     * Changes the current session for a Minecraft account to the provided session result.
     * @param sessionResult The new session result to use for the Minecraft account.
     */
    fun changeSession(sessionResult: MinecraftSessionResult)
}
