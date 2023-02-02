package tk.teaclient.auth.jsonobjects.res

import com.google.gson.annotations.SerializedName

data class MinecraftProfileErrorRes(
    @field:SerializedName("path") val path: String,
    @field:SerializedName("errorType") val errorType: String,
    @field:SerializedName("error") val error: String,
    @field:SerializedName("errorMessage") val errorMessage: String,
    @field:SerializedName("developerMessage") val developerMessage: String
)