package tk.teaclient.auth.jsonobjects.res

import com.google.gson.annotations.SerializedName

data class MinecraftProfileRes(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("skins") val skins: Array<Skin>,
    @field:SerializedName("capes") val capes: Array<Any> /* I don't have the cape object atm */
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MinecraftProfileRes) return false

        if (id != other.id) return false
        if (name != other.name) return false
        if (!skins.contentEquals(other.skins)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + skins.contentHashCode()
        return result
    }
}

data class Skin(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("state") val state: String,
    @field:SerializedName("url") val url: String,
    @field:SerializedName("variant") val variant: String,
    @field:SerializedName("alias") val alias: String
)