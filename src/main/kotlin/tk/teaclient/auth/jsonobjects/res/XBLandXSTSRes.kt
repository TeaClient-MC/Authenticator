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

/**
 * Represents a response from Microsoft Xbox Live and Xbox Secure Token Service (XBL and XSTS).
 *
 * @property issueInstant The time when the token was issued, in ISO 8601 format.
 * @property notAfter The time when the token will expire, in ISO 8601 format.
 * @property token The token string.
 * @property displayClaims The display claims associated with the token.
 */
data class XBLandXSTSRes(
    @field:SerializedName("IssueInstant") val issueInstant: String,
    @field:SerializedName("NotAfter") val notAfter: String,
    @field:SerializedName("Token") val token: String,
    @field:SerializedName("DisplayClaims") val displayClaims: DisplayClaims
)

/**
 * Represents the display claims associated with a Microsoft Xbox Live and Xbox Secure Token Service (XBL and XSTS) token.
 *
 * @property xui The list of Xbox User IDs (XUIs) associated with the token.
 */
data class DisplayClaims(@field:SerializedName("xui") val xui: List<XUI>)

/**
 * Represents a Microsoft Xbox User ID (XUI).
 *
 * @property uhs The user hash string associated with the XUI.
 */
data class XUI(@field:SerializedName("uhs") val uhs: String)
