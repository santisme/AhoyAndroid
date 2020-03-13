package io.santisme.ahoy.domain.models

import android.widget.ImageView
import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("id") val id: Int,
    @SerializedName("username") val username: String,
    @SerializedName("name") val name: String?,
    @SerializedName("avatar_template") val avatarTemplate: String?,
    @SerializedName("last_seen_at") val lastSeenAt: String?, // "2020-02-25T23:19:53.817Z"
    @SerializedName("moderator") val moderator: Boolean?,
    @SerializedName("logged") val logged: Boolean
)

data class UserModelWrapper(val userModel: UserModel, val userAvatarImage: ImageView?)
