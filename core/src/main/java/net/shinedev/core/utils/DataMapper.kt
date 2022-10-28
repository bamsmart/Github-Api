package net.shinedev.core.utils

import net.shinedev.core.data.source.local.entity.UserEntity
import net.shinedev.core.data.source.remote.response.UserResponse
import net.shinedev.core.domain.model.User
import net.shinedev.core.data.model.UserImpl

object DataMapper {
    fun mapResponsesToEntities(input: List<UserResponse>): List<UserEntity> {
        val userList = ArrayList<UserEntity>()
        input.map {
            val user = UserEntity(
                id = it.id,
                username = it.username,
                biography = it.biography ?: "",
                avatar = it.avatar,
                name = it.name,
                company = it.company,
                location = it.location,
                follower = it.follower,
                following = it.following,
                isFavorite = it.isFavorite
            )
            userList.add(user)
        }
        return userList
    }

    fun mapEntitiesToDomain(input: List<UserEntity>?): List<User>? =
        input?.map {
            UserImpl(
                id = it.id,
                username = it.username,
                biography = it.biography ?: "",
                avatar = it.avatar ?: "",
                name = it.name ?: "",
                company = it.company ?: "",
                location = it.location ?: "",
                follower = it.follower,
                following = it.following,
                isFavorite = it.isFavorite ?: false
            )
        }

    fun mapDomainToEntity(input: User) = UserEntity(
        id = input.id,
        username = input.username ?: "",
        biography = input.biography,
        avatar = input.avatar,
        name = input.name,
        company = input.company,
        location = input.location,
        follower = input.follower,
        following = input.following,
        isFavorite = input.isFavorite
    )
}