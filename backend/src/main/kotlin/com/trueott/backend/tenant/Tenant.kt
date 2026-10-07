package com.trueott.backend.tenant

import jakarta.persistence.*
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "tenants")
class Tenant(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(nullable = false, unique = true)
    var name: String,

    @Column(nullable = false, unique = true)
    var slug: String,

    var primaryColor: String = "#E50914",
    var logoUrl: String? = null,
    var isActive: Boolean = true,
    val createdAt: Instant = Instant.now()
)
