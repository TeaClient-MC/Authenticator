package dev.teaclient.auth.jsonobjects.res

import com.google.gson.annotations.SerializedName

/**
 * Response object for a failed request to get a Minecraft profile.
 *
 * @property path The path of the request that failed.
 * @property errorType The type of error that occurred.
 * @property error The specific error that occurred.
 * @property errorMessage A human-readable message describing the error.
 * @property developerMessage A developer-friendly message describing the error.
 */
data class MinecraftProfileErrorRes(
    @field:SerializedName("path") val path: String,
    @field:SerializedName("errorType") val errorType: String,
    @field:SerializedName("error") val error: String,
    @field:SerializedName("errorMessage") val errorMessage: String,
    @field:SerializedName("developerMessage") val developerMessage: String
)
