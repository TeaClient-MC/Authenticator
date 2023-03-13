package dev.teaclient.auth.jsonobjects.res

import com.google.gson.annotations.SerializedName

data class OwnershipRes(
    @SerializedName("items") val items: List<Item>,
    @SerializedName("signature") val signature: String,
    @SerializedName("keyId") val keyId: String
) {
    data class Item(
        @SerializedName("name") val name: String,
        @SerializedName("signature") val signature: String
    )
}
