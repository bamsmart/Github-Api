package net.shinedev.core.base

import com.google.gson.annotations.SerializedName

open class BaseResponse(
    @field:SerializedName("errors")
    val errors: ErrorResponse? = null
)

data class ErrorResponse(
    @field:SerializedName("resource")
    val resource: String? = null,

    @field:SerializedName("field")
    val field: String? = null,

    @field:SerializedName("code")
    val code: String? = null
)