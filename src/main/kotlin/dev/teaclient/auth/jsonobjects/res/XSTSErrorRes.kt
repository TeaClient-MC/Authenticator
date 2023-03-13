package dev.teaclient.auth.jsonobjects.res

import com.google.gson.annotations.SerializedName

/**
 * A data class representing an error response from XSTS authentication.
 *
 * @property identity The identity of the user that encountered the error.
 * @property err The error code associated with the error.
 * @property message The error message describing the issue that occurred.
 * @property redirect The URL to redirect the user to if necessary.
 */
data class XSTSErrorRes(
    @field:SerializedName("Identity") val identity: String,
    @field:SerializedName("XErr") val err: Long,
    @field:SerializedName("Message") val message: String,
    @field:SerializedName("Redirect") val redirect: String
)
