package dev.teaclient.auth.jsonobjects.res

import com.google.gson.annotations.SerializedName

/**
 * Represents an access token obtained from a Microsoft authentication service.
 *
 * @property tokenType The type of the access token. Usually "Bearer".
 * @property scope The scope of the access token, indicating the permissions it grants.
 * @property expiresIn The duration of time (in seconds) until the access token expires.
 * @property extExpiresIn The duration of time (in seconds) until the access token can no longer be extended.
 * @property accessToken The access token string.
 */
data class MicrosoftAccessTokenRes(
    @SerializedName("token_type") val tokenType: String,
    @SerializedName("scope") val scope: String,
    @SerializedName("expires_in") val expiresIn: Long,
    @SerializedName("ext_expires_in") val extExpiresIn: Long,
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String
)
