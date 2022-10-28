package net.shinedev.core.data.source.remote.response

import com.google.gson.annotations.SerializedName
import net.shinedev.core.base.BaseResponse

data class ListUserResponse(
    @field:SerializedName("items")
    val data: List<UserResponse>
) : BaseResponse()