package com.trueott.backend.auth

import com.trueott.backend.common.ApiResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
class AuthController {

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ApiResponse<LoginResponse> {
        val mockResponse = LoginResponse(
            accessToken = "mock-jwt-token-for-${request.email}",
            user = UserInfo(
                id = "usr_001",
                email = request.email,
                name = "Test User",
                role = "VIEWER"
            ),
            tenant = TenantInfo(
                id = "ten_001",
                name = "My OTT Platform",
                slug = request.tenantSlug,
                primaryColor = "#E50914"
            )
        )
        return ApiResponse.ok(mockResponse)
    }
}
