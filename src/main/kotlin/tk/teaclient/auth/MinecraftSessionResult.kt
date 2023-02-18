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

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Represents the result of a successful Microsoft login to Minecraft.
 *
 * @param username The username of the authenticated user.
 * @param uuid The UUID of the authenticated user.
 * @param token The access token of the authenticated user.
 */
data class MinecraftSessionResult(
    @field:SerializedName("username") val username: String,
    @field:SerializedName("uuid") val uuid: UUID,
    @field:SerializedName("token") val token: String
)
