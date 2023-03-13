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
package dev.teaclient.auth.jsonobjects.res

import com.google.gson.annotations.SerializedName

/**
 * Represents a response from the Minecraft authentication server, containing information about
 * the authenticated user.
 *
 * @property username The username of the authenticated user.
 * @property roles A list of roles assigned to the authenticated user.
 * @property accessToken The access token associated with the authenticated user.
 * @property tokenType The type of the access token associated with the authenticated user.
 * @property expiresIn The number of seconds until the access token associated with the authenticated
 *                     user expires.
 */
data class MinecraftRes(
    @field:SerializedName("username") val username: String,
    @field:SerializedName("roles") val roles: List<*>,
    @field:SerializedName("access_token") val accessToken: String,
    @field:SerializedName("token_type") val tokenType: String,
    @field:SerializedName("expires_in") val expiresIn: Long
)
