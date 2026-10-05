package com.trueott.backend.auth

data class LoginRequest(
    val email: String,
    val password: String,
    val tenantSlug: String
)

data class LoginResponse(
    val accessToken: String,
    val tokenType: String = "Bearer",
    val user: UserInfo,
    val tenant: TenantInfo
)

data class UserInfo(
    val id: String,
    val email: String,
    val name: String,
    val role: String
)

data class TenantInfo(
    val id: String,
    val name: String,
    val slug: String,
    val primaryColor: String
)
