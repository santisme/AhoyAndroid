package io.santisme.ahoy.domain.models.local

data class UserDetailModel(
    val id: Int,
    val username: String,
    val name: String?,
    val avatarTemplate: String?,
    val lastSeenAt: String?, // "2020-02-25T23:19:53.817Z"
    val moderator: Boolean?,
    val logged: Boolean
)