package net.shinedev.core.data.source.remote.network

import net.shinedev.core.data.source.remote.response.ListUserResponse
import net.shinedev.core.data.source.remote.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApiService {

    companion object {
        private const val USER = "users"
        const val SEARCH_USER = "search/$USER"
        const val DETAIL_USER = "$USER/{username}"
        const val LIST_FOLLOWER = "$USER/{username}/followers"
        const val LIST_FOLLOWING = "$USER/{username}/following"
    }

    @GET(SEARCH_USER)
    suspend fun getListUser(
        @Query("q") queryParams: String?
    ): ListUserResponse

    @GET(DETAIL_USER)
    suspend fun getDetailUser(
        @Path("username") username: String?
    ): UserResponse

    @GET(LIST_FOLLOWER)
    suspend fun getFollower(
        @Path("username") username: String?
    ): List<UserResponse>

    @GET(LIST_FOLLOWING)
    suspend fun getFollowing(
        @Path("username") username: String?
    ): List<UserResponse>
}