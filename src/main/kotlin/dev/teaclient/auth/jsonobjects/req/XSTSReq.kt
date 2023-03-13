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
package dev.teaclient.auth.jsonobjects.req

import com.google.gson.annotations.SerializedName

/**
 * Request object used for obtaining XSTS tokens.
 *
 * @property properties The properties of the request.
 * @property relyingParty The relying party of the request.
 * @property tokenType The token type of the request.
 */
data class XSTSReq(
    @field:SerializedName("Properties") val properties: XSTSProperties,
    @field:SerializedName("RelyingParty") val relyingParty: String = "rp://api.minecraftservices.com/",
    @field:SerializedName("TokenType") val tokenType: String = "JWT"
)

/**
 * The properties of an XSTS token request.
 *
 * @property sandboxId The sandbox ID of the request.
 * @property userTokens The user tokens of the request.
 */
data class XSTSProperties(
    @field:SerializedName("SandboxId") val sandboxId: String = "RETAIL",
    @field:SerializedName("UserTokens") val userTokens: List<String>
)