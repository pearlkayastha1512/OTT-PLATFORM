package com.example.ott_platform.tenant

import androidx.compose.ui.graphics.Color

/**
 * TenantType defines the industry or domain of the white-label tenant.
 */
enum class TenantType {
    COLLEGE,
    GYM,
    COACHING,
    GENERAL_OTT
}

/**
 * Brand color tokens configured per tenant.
 */
data class TenantColors(
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val background: Color,
    val surface: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val brandPrefixColor: Color,
    val brandSuffixColor: Color,
    val ambientGlowColors: List<Color>
)

/**
 * Visual branding configuration for a tenant.
 */
data class TenantBranding(
    val appName: String,
    val appNamePrefix: String,
    val appNameSuffix: String,
    val tagline: String,
    val logoResId: Int? = null,
    val colors: TenantColors
)

/**
 * Icons that can be rendered dynamically on floating cards without hard-coding drawables.
 */
enum class CardIconType {
    ACADEMIC_CAP,
    LIVE_EVENTS,
    CLAPPERBOARD,
    DUMBBELL,
    TIMER,
    BOOK_OPEN,
    SPARKLES
}

/**
 * Model representing a floating content card in the onboarding artwork.
 */
data class FloatingCardItem(
    val title: String,
    val subtitle: String? = null,
    val iconType: CardIconType? = null,
    val gradientColors: List<Color>,
    val rotationDegrees: Float,
    val offsetXDp: Float = 0f,
    val offsetYDp: Float = 0f,
    val showAccentBar: Boolean = true
)

/**
 * Sealed artwork hierarchy so onboarding pages can host different artwork types
 * (floating 3D cards, illustration vectors, promo banners, etc.) from configuration.
 */
sealed class OnboardingArtwork {
    data class FloatingCards(
        val cards: List<FloatingCardItem>,
        val glowColor: Color? = null
    ) : OnboardingArtwork()

    data class CustomIllustration(
        val illustrationResId: Int
    ) : OnboardingArtwork()
}

/**
 * Supported page types for onboarding flow.
 */
enum class OnboardingPageType {
    FEATURE_HIGHLIGHT,
    SHOWCASE_CARDS,
    AUTH_LANDING
}

/**
 * Call-to-action button definition.
 */
data class OnboardingCta(
    val text: String,
    val actionType: CtaActionType
)

enum class CtaActionType {
    NEXT_PAGE,
    SKIP_TO_AUTH,
    COMPLETE_ONBOARDING,
    CONTINUE_GOOGLE,
    BROWSE_GUEST
}

/**
 * Comprehensive page configuration for an onboarding screen.
 */
data class OnboardingPageConfig(
    val id: String,
    val pageType: OnboardingPageType,
    val title: String,
    val description: String,
    val artwork: OnboardingArtwork,
    val primaryCta: OnboardingCta? = null,
    val secondaryCta: OnboardingCta? = null,
    val showSkip: Boolean = true
)

/**
 * Authentication & guest access configuration for the tenant.
 */
data class TenantAuthConfig(
    val googleEnabled: Boolean = true,
    val googleButtonText: String = "Continue with Google",
    val guestAccessEnabled: Boolean = true,
    val guestAccessText: String = "Browse as guest",
    val guestAccessDescription: String = "Guests can explore public tracks, trailers, and preview showcases."
)

/**
 * Complete White-Label Multi-Tenant Configuration Model.
 * Can be serialized/deserialized from remote JSON / CMS backend in production.
 */
data class TenantConfig(
    val tenantId: String,
    val tenantType: TenantType,
    val branding: TenantBranding,
    val authConfig: TenantAuthConfig,
    val onboardingPages: List<OnboardingPageConfig>
)
