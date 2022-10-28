package net.shinedev.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import net.shinedev.core.data.Resource
import net.shinedev.core.domain.model.User

interface UserUseCase {
    fun getListUser(queryParams: String): Flow<Resource<List<User>>>
    fun getDetailUser(username: String):  Flow<Resource<User>>
    fun getFollower(username: String): Flow<Resource<List<User>>>
    fun getFollowing(username: String): Flow<Resource<List<User>>>
    fun setFavoriteUser(user: User, isFavorite: Boolean): Flow<Int>
    fun getFavoriteUser(queryParams: String): Flow<Resource<List<User>>>
}