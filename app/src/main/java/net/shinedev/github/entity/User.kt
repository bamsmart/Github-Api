package net.shinedev.github.entity

import android.content.ContentValues
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import net.shinedev.github.db.DatabaseContract.UserColumns.Companion.AVATAR
import net.shinedev.github.db.DatabaseContract.UserColumns.Companion.COMPANY
import net.shinedev.github.db.DatabaseContract.UserColumns.Companion.FOLLOWERS
import net.shinedev.github.db.DatabaseContract.UserColumns.Companion.FOLLOWING
import net.shinedev.github.db.DatabaseContract.UserColumns.Companion.ID
import net.shinedev.github.db.DatabaseContract.UserColumns.Companion.IS_FAVORITE
import net.shinedev.github.db.DatabaseContract.UserColumns.Companion.LOCATION
import net.shinedev.github.db.DatabaseContract.UserColumns.Companion.NAME
import net.shinedev.github.db.DatabaseContract.UserColumns.Companion.USERNAME
import net.shinedev.github.db.DatabaseContract.UserColumns.Companion.USER_ID
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    var ids: Int? = 0,

    @SerializedName("id")
    @Expose
    var userId: Int? = 0,

    @SerializedName("login")
    @Expose
    var username: String,

    @SerializedName("avatar_url")
    @Expose
    var avatar: String? = null,

    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("company")
    @Expose
    var company: String? = null,

    @SerializedName("location")
    @Expose
    var location: String? = null,

    @SerializedName("followers")
    @Expose
    var follower: Int? = 0,

    @SerializedName("following")
    @Expose
    var following: Int? = 0,

    @SerializedName("is_favorite")
    @Expose
    var isFavorite: Boolean = false,
) : Parcelable {
    companion object {
        fun fromContentValues(contentValues: ContentValues): User {
            val user = User(0, 0, "", "", "", "", "", 0, 0, false)
            if (contentValues.containsKey(ID)) {
                user.ids = contentValues.getAsInteger(ID)
            }
            if (contentValues.containsKey(USER_ID)) {
                user.userId = contentValues.getAsInteger(USER_ID)
            }
            if (contentValues.containsKey(USERNAME)) {
                user.username = contentValues.getAsString(USERNAME)
            }
            if (contentValues.containsKey(AVATAR)) {
                user.avatar = contentValues.getAsString(AVATAR)
            }
            if (contentValues.containsKey(NAME)) {
                user.name = contentValues.getAsString(NAME)
            }
            if (contentValues.containsKey(NAME)) {
                user.name = contentValues.getAsString(NAME)
            }
            if (contentValues.containsKey(COMPANY)) {
                user.company = contentValues.getAsString(COMPANY)
            }
            if (contentValues.containsKey(LOCATION)) {
                user.location = contentValues.getAsString(LOCATION)
            }
            if (contentValues.containsKey(FOLLOWERS)) {
                user.follower = contentValues.getAsInteger(FOLLOWERS)
            }
            if (contentValues.containsKey(FOLLOWING)) {
                user.following = contentValues.getAsInteger(FOLLOWING)
            }
            if (contentValues.containsKey(IS_FAVORITE)) {
                user.isFavorite = contentValues.getAsBoolean(IS_FAVORITE)
            }
            return user
        }
    }

}
