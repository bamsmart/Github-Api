package net.shinedev.core.domain.model

import android.os.Parcelable

interface User : Parcelable {
    val id: Int
    val username: String?
    val biography : String?
    val avatar: String?
    val name: String?
    val company: String?
    val location: String?
    val follower: Int
    val following: Int
    val isFavorite: Boolean?
}