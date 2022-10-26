package net.shinedev.github.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {

    companion object {
        private const val USER = "users"
        const val SEARCH_USER = "search/$USER"
        const val DETAIL_USER = "$USER/{username}"
        const val LIST_FOLLOWER = "$USER/{username}/followers"
        const val LIST_FOLLOWING = "$USER/{username}/following"
    }

    @GET(SEARCH_USER)
    fun getListUser(
        @Header("Authorization") token: String,
        @Query("q") username: String?
    ): Call<ResponseBody>

    @GET(DETAIL_USER)
    fun getDetailUser(
        @Header("Authorization") token: String,
        @Path("username") username: String?
    ): Call<ResponseBody>

    @GET(LIST_FOLLOWER)
    fun getFollower(
        @Header("Authorization") token: String,
        @Path("username") username: String?
    ): Call<ResponseBody>

    @GET(LIST_FOLLOWING)
    fun getFollowing(
        @Header("Authorization") token: String,
        @Path("username") username: String?
    ): Call<ResponseBody>
}