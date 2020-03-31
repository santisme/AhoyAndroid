package io.santisme.ahoy.domain.models.local

import android.widget.ImageView

data class PostCellModel(
    val avatarTemplate: String?,
    val userImage: ImageView?,
    val username: String,
    val postContent: String,
    val updatedAt: String
)