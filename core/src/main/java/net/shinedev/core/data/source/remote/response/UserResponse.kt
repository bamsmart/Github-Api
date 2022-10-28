package net.shinedev.core.data.source.remote.response

import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import net.shinedev.core.base.BaseResponse
import net.shinedev.core.domain.model.User

@Parcelize
data class UserResponse(
    @field:SerializedName("id")
    override val id: Int,

    @field:SerializedName("login")
    override val username: String,

    @field:SerializedName("bio")
    override val biography: String? = null,

    @field:SerializedName("avatar_url")
    override val avatar: String,

    @field:SerializedName("name")
    override val name: String,

    @field:SerializedName("company")
    override val company: String,

    @field:SerializedName("location")
    override val location: String,

    @field:SerializedName("followers")
    override val follower: Int,

    @field:SerializedName("following")
    override val following: Int,

    override val isFavorite: Boolean,
) : User, BaseResponse()

fun User.toResponse(): UserResponse = UserResponse(
    id = this.id,
    username = this.username ?: "",
    biography = this.biography ?: "",
    avatar = this.avatar ?: "",
    name = this.name ?: "",
    company = this.company ?: "",
    location = this.location ?: "",
    follower = this.follower,
    following = this.following,
    isFavorite = this.isFavorite ?: false
)