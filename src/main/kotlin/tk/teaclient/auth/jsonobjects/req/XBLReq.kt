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

data class XBLReq(
    @field:SerializedName("Properties") val properties: Properties,
    @field:SerializedName("RelyingParty") val relyingParty: String = "http://auth.xboxlive.com",
    @field:SerializedName("TokenType") val tokenType: String = "JWT"
)

data class Properties(
    @field:SerializedName("AuthMethod") val authMethod: String = "RPS",
    @field:SerializedName("SiteName") val siteName: String = "user.auth.xboxlive.com",
    @field:SerializedName("RpsTicket") val rpsTicket: String
)