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

data class XBLandXSTSRes(
    @field:SerializedName("IssueInstant") val issueInstant: String,
    @field:SerializedName("NotAfter") val notAfter: String,
    @field:SerializedName("Token") val token: String,
    @field:SerializedName("DisplayClaims") val displayClaims: DisplayClaims
)

data class DisplayClaims(@field:SerializedName("xui") val xui: Array<XUI>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DisplayClaims) return false

        if (!xui.contentEquals(other.xui)) return false

        return true
    }

    override fun hashCode(): Int {
        return xui.contentHashCode()
    }
}

data class XUI(@field:SerializedName("uhs") val uhs: String)