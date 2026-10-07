package com.trueott.backend.auth

import com.trueott.backend.common.ApiResponse
import com.trueott.backend.tenant.TenantRepository
import com.trueott.backend.user.UserRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val tenantRepository: TenantRepository,
    private val userRepository: UserRepository
) {

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ApiResponse<LoginResponse> {
        // 1. Database se check karo ki Tenant exist karta hai ya nahi
        val tenant = tenantRepository.findBySlug(request.tenantSlug)
            ?: return ApiResponse.error("TENANT_NOT_FOUND", "Tenant '${request.tenantSlug}' does not exist.")

        // 2. Is specific Tenant ke andar User search karo
        val user = userRepository.findByEmailAndTenantId(request.email, tenant.id!!)
            ?: return ApiResponse.error("INVALID_CREDENTIALS", "Invalid email or password.")

        // 3. Password verify karo
        if (user.passwordHash != request.password) {
            return ApiResponse.error("INVALID_CREDENTIALS", "Invalid email or password.")
        }

        // 4. PostgreSQL se real user aur tenant ka data return karo!
        val response = LoginResponse(
            accessToken = "jwt-${user.id}-${System.currentTimeMillis()}",
            user = UserInfo(
                id = user.id.toString(),
                email = user.email,
                name = user.name,
                role = user.role
            ),
            tenant = TenantInfo(
                id = tenant.id.toString(),
                name = tenant.name,
                slug = tenant.slug,
                primaryColor = tenant.primaryColor
            )
        )
        return ApiResponse.ok(response)
    }
}
