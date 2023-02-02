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
package tk.teaclient.auth.jsonobjects.res

import com.google.gson.annotations.SerializedName

data class MinecraftRes(
    @field:SerializedName("username") val username: String,
    @field:SerializedName("roles") val roles: Array<*>,
    @field:SerializedName("access_token") val accessToken: String,
    @field:SerializedName("token_type") val tokenType: String,
    @field:SerializedName("expires_in") val expiresIn: Long
) {
    override fun equals(other: Any?): Boolean {
        if(other == null) return false
        if (this === other) return true
        if (other !is MinecraftRes) return false

        if (username != other.username) return false
        if (!roles.contentEquals(other.roles)) return false
        if (accessToken != other.accessToken) return false
        if (tokenType != other.tokenType) return false
        if (expiresIn != other.expiresIn) return false

        return true
    }

    override fun hashCode(): Int {
        var result = username.hashCode()
        result = 31 * result + roles.contentHashCode()
        result = 31 * result + accessToken.hashCode()
        result = 31 * result + tokenType.hashCode()
        result = 31 * result + expiresIn.hashCode()
        return result
    }
}