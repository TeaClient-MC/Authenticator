package tk.teaclient.auth.jsonobjects.res

import com.google.gson.annotations.SerializedName

/**
 * Response object containing Minecraft profile data.
 *
 * @property id The UUID of the player.
 * @property name The username of the player.
 * @property skins An array of [Skin] objects representing the player's skins.
 * @property capes An array of [Cape] objects representing the player's capes.
 */
data class MinecraftProfileRes(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("skins") val skins: List<Skin>,
    @field:SerializedName("capes") val capes: List<Cape>
) {
    /**
     * Response object containing Minecraft skin data.
     *
     * @property id The ID of the skin.
     * @property state The state of the skin.
     * @property url The URL of the skin.
     * @property variant The variant of the skin.
     * @property alias The alias of the skin.
     */
    data class Skin(
        @field:SerializedName("id") val id: String,
        @field:SerializedName("state") val state: String,
        @field:SerializedName("url") val url: String,
        @field:SerializedName("variant") val variant: String,
        @field:SerializedName("alias") val alias: String
    )

    /**
     * Response object containing Minecraft cape data.
     *
     * @property id The ID of the cape.
     * @property state The state of the cape.
     * @property url The URL of the cape.
     */
    data class Cape(
        @field:SerializedName("id") val id: String,
        @field:SerializedName("state") val state: String,
        @field:SerializedName("url") val url: String
    )
}
