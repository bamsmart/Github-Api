package net.shinedev.core.data.model

import kotlinx.parcelize.Parcelize
import net.shinedev.core.domain.model.User

@Parcelize
data class UserImpl(
    override val id: Int = 0,
    override val username: String = "",
    override val biography: String = "",
    override val avatar: String = "",
    override val name: String = "",
    override val company: String = "",
    override val location: String = "",
    override val follower: Int = 0,
    override val following: Int = 0,
    override val isFavorite: Boolean = false
) : User