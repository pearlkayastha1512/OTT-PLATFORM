package com.trueott.backend.common

import com.trueott.backend.tenant.Tenant
import com.trueott.backend.tenant.TenantRepository
import com.trueott.backend.user.User
import com.trueott.backend.user.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DataInitializer(
    private val tenantRepository: TenantRepository,
    private val userRepository: UserRepository
) : CommandLineRunner {

    override fun run(vararg args: String) {
        // Agar demo tenant pehle se nahi hai toh create karo
        if (!tenantRepository.existsBySlug("demo-ott")) {
            val demoTenant = tenantRepository.save(
                Tenant(
                    name = "Demo OTT Hub",
                    slug = "demo-ott",
                    primaryColor = "#E50914"
                )
            )

            // Demo user create karo
            userRepository.save(
                User(
                    email = "pearl@example.com",
                    passwordHash = "password123", // Abhi plain text, next sprint me BCrypt hashing add karenge
                    name = "Pearl Kayastha",
                    role = "ADMIN",
                    tenant = demoTenant
                )
            )
            println(">>> SEED DATA: Demo Tenant & User created in PostgreSQL! <<<")
        }
    }
}
