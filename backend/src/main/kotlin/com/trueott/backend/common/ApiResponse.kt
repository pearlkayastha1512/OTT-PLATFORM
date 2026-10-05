package com.trueott.backend.common

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.Instant

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val error: ApiError? = null,
    val timestamp: Instant = Instant.now()
) {
    companion object {
        fun <T> ok(data: T): ApiResponse<T> = ApiResponse(success = true, data = data)
        fun error(code: String, message: String): ApiResponse<Nothing> =
            ApiResponse(success = false, error = ApiError(code, message))
    }
}

data class ApiError(
    val code: String,
    val message: String
)
