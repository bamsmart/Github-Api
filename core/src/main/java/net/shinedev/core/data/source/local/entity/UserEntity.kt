package net.shinedev.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import net.shinedev.core.domain.model.User
import javax.annotation.Nullable

@Entity
@Parcelize
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "user_id")
    override var id: Int,

    @ColumnInfo(name = "user_name")
    override var username: String = "",

    @ColumnInfo(name = "bio")
    @Nullable
    override val biography: String? = "",

    @ColumnInfo(name = "avatar")
    @Nullable
    override var avatar: String? = "",

    @ColumnInfo(name = "name")
    @Nullable
    override var name: String? = "",

    @ColumnInfo(name = "company")
    @Nullable
    override var company: String? = "",

    @ColumnInfo(name = "location")
    @Nullable
    override var location: String? = "",

    @ColumnInfo(name = "followers")
    override var follower: Int = 0,

    @ColumnInfo(name = "following")
    override var following: Int = 0,

    @ColumnInfo(name = "is_favorite")
    @Nullable
    override var isFavorite: Boolean? = false,
) : User
