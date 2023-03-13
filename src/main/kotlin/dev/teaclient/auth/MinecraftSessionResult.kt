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
package dev.teaclient.auth

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Represents the result of a successful Minecraft authentication session.
 *
 * @property username The username of the authenticated user.
 * @property uuid The UUID of the authenticated user.
 * @property accessToken The access token of the authenticated user.
 * @property refreshToken The refresh token, used to get new access token.
 */
data class MinecraftSessionResult(
    @SerializedName("username") val username: String,
    @SerializedName("uuid") val uuid: UUID,
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String
)
