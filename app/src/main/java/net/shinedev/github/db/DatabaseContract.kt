package net.shinedev.github.db

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {
    const val AUTHORITY = "com.amartha.dicoding.mysubmission3"
    const val SCHEME = "content"

    internal class UserColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "User"
            const val ID = "id"
            const val USER_ID = "userId"
            const val USERNAME = "login"
            const val NAME = "name"
            const val AVATAR = "avatar_url"
            const val COMPANY = "company"
            const val LOCATION = "location"
            const val FOLLOWERS = "followers"
            const val FOLLOWING = "following"
            const val IS_FAVORITE = "is_favorite"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}