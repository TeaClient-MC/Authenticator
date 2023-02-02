package tk.teaclient.auth.jsonobjects.res

import com.google.gson.annotations.SerializedName

data class XSTSErrorRes(
    @field:SerializedName("Identity") val identity: String,
    @field:SerializedName("XErr") val err: Long,
    @field:SerializedName("Message") val message: String,
    @field:SerializedName("Redirect") val redirect: String
)