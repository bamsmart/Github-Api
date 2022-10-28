package net.shinedev.core.domain.repository

import kotlinx.coroutines.flow.Flow
import net.shinedev.core.data.Resource
import net.shinedev.core.data.source.remote.network.ApiResponse
import net.shinedev.core.domain.model.User

interface UserRepository {
    fun getListUser(queryParams: String): Flow<Resource<List<User>>>
    fun getDetailUser(username: String): Flow<Resource<User>>
    fun getFollowers(username: String): Flow<Resource<List<User>>>
    fun getFollowings(username: String): Flow<Resource<List<User>>>
    fun setFavoriteUser(user: User, isFavorite: Boolean): Flow<Int>
    fun getFavoriteUser(queryParams: String): Flow<Resource<List<User>>>

    suspend fun getProfile(username: String): Flow<ApiResponse<User>>
}