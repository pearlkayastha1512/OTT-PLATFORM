package com.trueott.backend.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository : JpaRepository<User, UUID> {
    fun findByEmailAndTenantId(email: String, tenantId: UUID): User?
    fun existsByEmailAndTenantId(email: String, tenantId: UUID): Boolean
}
