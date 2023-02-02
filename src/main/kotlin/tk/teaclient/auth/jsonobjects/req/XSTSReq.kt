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
package tk.teaclient.auth.jsonobjects.req

import com.google.gson.annotations.SerializedName

data class XSTSReq(
    @field:SerializedName("Properties") val properties: XSTSProperties,
    @field:SerializedName("RelyingParty") val relyingParty: String = "rp://api.minecraftservices.com/",
    @field:SerializedName("TokenType") val tokenType: String = "JWT"
)

data class XSTSProperties(
    @field:SerializedName("SandboxId") val sandboxId: String = "RETAIL",
    @field:SerializedName("UserTokens") val userTokens: Array<String>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is XSTSProperties) return false

        if (sandboxId != other.sandboxId) return false
        if (!userTokens.contentEquals(other.userTokens)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = sandboxId.hashCode()
        result = 31 * result + userTokens.contentHashCode()
        return result
    }
}
