package com.example.ott_platform.tenant

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import com.example.ott_platform.R
import com.example.ott_platform.ui.theme.TrueBlue
import com.example.ott_platform.ui.theme.TrueBlueLight
import com.example.ott_platform.ui.theme.TrueGreen
import com.example.ott_platform.ui.theme.TrueGreenLight

/**
 * TenantRepository manages tenant configurations.
 * In production, configurations can be fetched from a remote CDN/REST endpoint.
 */
object TenantRepository {

    // 1. College Tenant (Default, perfectly matching reference design)
    val CollegeTenant = TenantConfig(
        tenantId = "tenant_college_xyz",
        tenantType = TenantType.COLLEGE,
        branding = TenantBranding(
            appName = "xyzott",
            appNamePrefix = "xyz",
            appNameSuffix = "ott",
            tagline = "Learn it. Watch it. Make it.",
            logoResId = null,
            colors = TenantColors(
                primary = Color(0xFFFFEB3B), // Vibrant Lemon Yellow CTA (#FFE500 / #FFEB3B)
                onPrimary = Color(0xFF0F101A),
                secondary = Color(0xFF8B5CF6), // Purple Accent
                background = Color(0xFF0D0E1F), // Deep Indigo Midnight
                surface = Color(0xFF161833),
                textPrimary = Color.White,
                textSecondary = Color(0xFFB0B7D1),
                brandPrefixColor = Color.White,
                brandSuffixColor = Color(0xFFFFEB3B),
                ambientGlowColors = listOf(
                    Color(0xFF5B21B6).copy(alpha = 0.55f),
                    Color(0xFF1D4ED8).copy(alpha = 0.35f),
                    Color(0xFFBE185D).copy(alpha = 0.25f)
                )
            )
        ),
        authConfig = TenantAuthConfig(
            googleEnabled = true,
            googleButtonText = "Continue with Google",
            guestAccessEnabled = true,
            guestAccessText = "Browse as guest",
            guestAccessDescription = "Guests can watch XYZ Originals, fests and startup showcases."
        ),
        onboardingPages = listOf(
            OnboardingPageConfig(
                id = "college_features",
                pageType = OnboardingPageType.FEATURE_HIGHLIGHT,
                title = "All Your College Content\nin One Place",
                description = "Lectures, live events, shorts and more — for every branch and semester.",
                artwork = OnboardingArtwork.FloatingCards(
                    cards = listOf(
                        FloatingCardItem(
                            title = "Lectures\n& Courses",
                            iconType = CardIconType.ACADEMIC_CAP,
                            gradientColors = listOf(Color(0xFFEC4899), Color(0xFF8B5CF6)), // Pink to Purple
                            rotationDegrees = -12f,
                            offsetXDp = -48f,
                            offsetYDp = -24f
                        ),
                        FloatingCardItem(
                            title = "Live\nEvents",
                            iconType = CardIconType.LIVE_EVENTS,
                            gradientColors = listOf(Color(0xFF06B6D4), Color(0xFF3B82F6)), // Cyan to Blue
                            rotationDegrees = 11f,
                            offsetXDp = 46f,
                            offsetYDp = -14f
                        ),
                        FloatingCardItem(
                            title = "Shorts",
                            iconType = CardIconType.CLAPPERBOARD,
                            gradientColors = listOf(Color(0xFFFBBF24), Color(0xFFF43F5E)), // Amber to Hot Pink
                            rotationDegrees = -4f,
                            offsetXDp = -2f,
                            offsetYDp = 48f
                        )
                    )
                ),
                primaryCta = OnboardingCta("Next", CtaActionType.NEXT_PAGE),
                showSkip = true
            ),
            OnboardingPageConfig(
                id = "college_auth_landing",
                pageType = OnboardingPageType.AUTH_LANDING,
                title = "xyzott",
                description = "Learn it. Watch it. Make it.",
                artwork = OnboardingArtwork.FloatingCards(
                    cards = listOf(
                        FloatingCardItem(
                            title = "Freshers\nNight",
                            gradientColors = listOf(Color(0xFFF43F5E), Color(0xFFC084FC)),
                            rotationDegrees = -10f,
                            offsetXDp = -52f,
                            offsetYDp = 10f,
                            showAccentBar = false
                        ),
                        FloatingCardItem(
                            title = "Campus\nDiaries",
                            gradientColors = listOf(Color(0xFFA855F7), Color(0xFFF472B6)),
                            rotationDegrees = 1f,
                            offsetXDp = -4f,
                            offsetYDp = -36f,
                            showAccentBar = false
                        ),
                        FloatingCardItem(
                            title = "Around\nCampus",
                            gradientColors = listOf(Color(0xFFF59E0B), Color(0xFFF43F5E)),
                            rotationDegrees = 12f,
                            offsetXDp = 50f,
                            offsetYDp = 12f,
                            showAccentBar = false
                        )
                    )
                ),
                primaryCta = OnboardingCta("Continue with Google", CtaActionType.CONTINUE_GOOGLE),
                secondaryCta = OnboardingCta("Browse as guest", CtaActionType.BROWSE_GUEST),
                showSkip = false
            )
        )
    )

    // 2. Gym / Fitness Tenant Configuration
    val GymTenant = TenantConfig(
        tenantId = "tenant_gym_fitpulse",
        tenantType = TenantType.GYM,
        branding = TenantBranding(
            appName = "fitpulse",
            appNamePrefix = "fit",
            appNameSuffix = "pulse",
            tagline = "Train. Track. Transform.",
            logoResId = null,
            colors = TenantColors(
                primary = Color(0xFF10B981), // Emerald Neon Green
                onPrimary = Color(0xFF04170E),
                secondary = Color(0xFFF59E0B), // Energetic Amber
                background = Color(0xFF0A0F0D), // Carbon Deep Dark
                surface = Color(0xFF141F1A),
                textPrimary = Color.White,
                textSecondary = Color(0xFFA7C2B4),
                brandPrefixColor = Color.White,
                brandSuffixColor = Color(0xFF10B981),
                ambientGlowColors = listOf(
                    Color(0xFF059669).copy(alpha = 0.5f),
                    Color(0xFF10B981).copy(alpha = 0.3f),
                    Color(0xFFF59E0B).copy(alpha = 0.2f)
                )
            )
        ),
        authConfig = TenantAuthConfig(
            googleEnabled = true,
            googleButtonText = "Continue with Google",
            guestAccessEnabled = true,
            guestAccessText = "Browse as guest",
            guestAccessDescription = "Guests can explore gym tours, sample routines, and public masterclasses."
        ),
        onboardingPages = listOf(
            OnboardingPageConfig(
                id = "gym_features",
                pageType = OnboardingPageType.FEATURE_HIGHLIGHT,
                title = "All Your Fitness Content\nin One Place",
                description = "HIIT routines, live trainer classes, diet plans, and recovery tracks.",
                artwork = OnboardingArtwork.FloatingCards(
                    cards = listOf(
                        FloatingCardItem(
                            title = "HIIT &\nStrength",
                            iconType = CardIconType.DUMBBELL,
                            gradientColors = listOf(Color(0xFF10B981), Color(0xFF059669)),
                            rotationDegrees = -11f,
                            offsetXDp = -48f,
                            offsetYDp = -24f
                        ),
                        FloatingCardItem(
                            title = "Live Trainer\nJam",
                            iconType = CardIconType.LIVE_EVENTS,
                            gradientColors = listOf(Color(0xFF06B6D4), Color(0xFF0284C7)),
                            rotationDegrees = 10f,
                            offsetXDp = 46f,
                            offsetYDp = -14f
                        ),
                        FloatingCardItem(
                            title = "Quick\nBurn",
                            iconType = CardIconType.TIMER,
                            gradientColors = listOf(Color(0xFFF97316), Color(0xFFEF4444)),
                            rotationDegrees = -4f,
                            offsetXDp = -2f,
                            offsetYDp = 48f
                        )
                    )
                ),
                primaryCta = OnboardingCta("Next", CtaActionType.NEXT_PAGE),
                showSkip = true
            ),
            OnboardingPageConfig(
                id = "gym_auth_landing",
                pageType = OnboardingPageType.AUTH_LANDING,
                title = "fitpulse",
                description = "Train. Track. Transform.",
                artwork = OnboardingArtwork.FloatingCards(
                    cards = listOf(
                        FloatingCardItem(
                            title = "Leg Day\nBlitz",
                            gradientColors = listOf(Color(0xFF10B981), Color(0xFF047857)),
                            rotationDegrees = -10f,
                            offsetXDp = -52f,
                            offsetYDp = 10f,
                            showAccentBar = false
                        ),
                        FloatingCardItem(
                            title = "Transformation\nHub",
                            gradientColors = listOf(Color(0xFF0284C7), Color(0xFF0D9488)),
                            rotationDegrees = 1f,
                            offsetXDp = -4f,
                            offsetYDp = -36f,
                            showAccentBar = false
                        ),
                        FloatingCardItem(
                            title = "Nutrition\nLab",
                            gradientColors = listOf(Color(0xFFF59E0B), Color(0xFFD97706)),
                            rotationDegrees = 12f,
                            offsetXDp = 50f,
                            offsetYDp = 12f,
                            showAccentBar = false
                        )
                    )
                ),
                primaryCta = OnboardingCta("Continue with Google", CtaActionType.CONTINUE_GOOGLE),
                secondaryCta = OnboardingCta("Browse as guest", CtaActionType.BROWSE_GUEST),
                showSkip = false
            )
        )
    )

    // 3. Coaching Institute Tenant Configuration
    val CoachingTenant = TenantConfig(
        tenantId = "tenant_coaching_apex",
        tenantType = TenantType.COACHING,
        branding = TenantBranding(
            appName = "apexott",
            appNamePrefix = "apex",
            appNameSuffix = "ott",
            tagline = "Master concepts. Conquer exams.",
            logoResId = null,
            colors = TenantColors(
                primary = Color(0xFF38BDF8), // Electric Cyan-Blue
                onPrimary = Color(0xFF031627),
                secondary = Color(0xFF6366F1), // Royal Indigo
                background = Color(0xFF070B16),
                surface = Color(0xFF10172A),
                textPrimary = Color.White,
                textSecondary = Color(0xFFA5B4CB),
                brandPrefixColor = Color.White,
                brandSuffixColor = Color(0xFF38BDF8),
                ambientGlowColors = listOf(
                    Color(0xFF0284C7).copy(alpha = 0.5f),
                    Color(0xFF4F46E5).copy(alpha = 0.35f)
                )
            )
        ),
        authConfig = TenantAuthConfig(
            googleEnabled = true,
            googleButtonText = "Continue with Google",
            guestAccessEnabled = true,
            guestAccessText = "Browse as guest",
            guestAccessDescription = "Guests can view syllabus blueprints, demo lectures, and topper masterclasses."
        ),
        onboardingPages = listOf(
            OnboardingPageConfig(
                id = "coaching_features",
                pageType = OnboardingPageType.FEATURE_HIGHLIGHT,
                title = "All Your Exam Prep\nin One Place",
                description = "Lectures, doubt jams, revision shorts and rank boosters.",
                artwork = OnboardingArtwork.FloatingCards(
                    cards = listOf(
                        FloatingCardItem(
                            title = "Full Syllabus\nLectures",
                            iconType = CardIconType.ACADEMIC_CAP,
                            gradientColors = listOf(Color(0xFF6366F1), Color(0xFF3B82F6)),
                            rotationDegrees = -11f,
                            offsetXDp = -48f,
                            offsetYDp = -24f
                        ),
                        FloatingCardItem(
                            title = "Live Doubt\nJam",
                            iconType = CardIconType.LIVE_EVENTS,
                            gradientColors = listOf(Color(0xFF0EA5E9), Color(0xFF06B6D4)),
                            rotationDegrees = 10f,
                            offsetXDp = 46f,
                            offsetYDp = -14f
                        ),
                        FloatingCardItem(
                            title = "Concept\nShorts",
                            iconType = CardIconType.TIMER,
                            gradientColors = listOf(Color(0xFFF59E0B), Color(0xFFEC4899)),
                            rotationDegrees = -4f,
                            offsetXDp = -2f,
                            offsetYDp = 48f
                        )
                    )
                ),
                primaryCta = OnboardingCta("Next", CtaActionType.NEXT_PAGE),
                showSkip = true
            ),
            OnboardingPageConfig(
                id = "coaching_auth_landing",
                pageType = OnboardingPageType.AUTH_LANDING,
                title = "apexott",
                description = "Master concepts. Conquer exams.",
                artwork = OnboardingArtwork.FloatingCards(
                    cards = listOf(
                        FloatingCardItem(
                            title = "Rank Booster\nSeries",
                            gradientColors = listOf(Color(0xFF3B82F6), Color(0xFF1D4ED8)),
                            rotationDegrees = -10f,
                            offsetXDp = -52f,
                            offsetYDp = 10f,
                            showAccentBar = false
                        ),
                        FloatingCardItem(
                            title = "PYQ\nMarathon",
                            gradientColors = listOf(Color(0xFF8B5CF6), Color(0xFF6366F1)),
                            rotationDegrees = 1f,
                            offsetXDp = -4f,
                            offsetYDp = -36f,
                            showAccentBar = false
                        ),
                        FloatingCardItem(
                            title = "AIR 1\nNotes",
                            gradientColors = listOf(Color(0xFF06B6D4), Color(0xFF0EA5E9)),
                            rotationDegrees = 12f,
                            offsetXDp = 50f,
                            offsetYDp = 12f,
                            showAccentBar = false
                        )
                    )
                ),
                primaryCta = OnboardingCta("Continue with Google", CtaActionType.CONTINUE_GOOGLE),
                secondaryCta = OnboardingCta("Browse as guest", CtaActionType.BROWSE_GUEST),
                showSkip = false
            )
        )
    )

    // 4. trueOTT Tenant Configuration
    val TrueOttTenant = TenantConfig(
        tenantId = "tenant_true_ott",
        tenantType = TenantType.GENERAL_OTT,
        branding = TenantBranding(
            appName = "trueOTT",
            appNamePrefix = "true",
            appNameSuffix = "OTT",
            tagline = "LEARN • WATCH • GROW",
            logoResId = R.drawable.trueott_emblem,
            colors = TenantColors(
                primary = TrueGreenLight,
                onPrimary = Color(0xFF061403),
                secondary = TrueBlueLight,
                background = Color(0xFF07090E),
                surface = Color(0xFF101B2C),
                textPrimary = Color.White,
                textSecondary = Color(0xFFA0A6B5),
                brandPrefixColor = TrueBlueLight,
                brandSuffixColor = TrueGreenLight,
                ambientGlowColors = listOf(
                    TrueBlue.copy(alpha = 0.5f),
                    TrueGreen.copy(alpha = 0.35f)
                )
            )
        ),
        authConfig = TenantAuthConfig(
            googleEnabled = true,
            googleButtonText = "Continue with Google",
            guestAccessEnabled = true,
            guestAccessText = "Browse as guest",
            guestAccessDescription = "Guests can watch original shows, documentaries, and premiere teasers."
        ),
        onboardingPages = listOf(
            OnboardingPageConfig(
                id = "trueott_features",
                pageType = OnboardingPageType.FEATURE_HIGHLIGHT,
                title = "All Your Favorite Content\nin One Place",
                description = "Original shows, live events, shorts and more — for curious minds everywhere.",
                artwork = OnboardingArtwork.FloatingCards(
                    cards = listOf(
                        FloatingCardItem(
                            title = "Originals &\nCourses",
                            iconType = CardIconType.ACADEMIC_CAP,
                            gradientColors = listOf(Color(0xFFEC4899), Color(0xFF8B5CF6)),
                            rotationDegrees = -12f,
                            offsetXDp = -48f,
                            offsetYDp = -24f
                        ),
                        FloatingCardItem(
                            title = "Live\nEvents",
                            iconType = CardIconType.LIVE_EVENTS,
                            gradientColors = listOf(Color(0xFF06B6D4), Color(0xFF3B82F6)),
                            rotationDegrees = 11f,
                            offsetXDp = 46f,
                            offsetYDp = -14f
                        ),
                        FloatingCardItem(
                            title = "Shorts &\nReels",
                            iconType = CardIconType.CLAPPERBOARD,
                            gradientColors = listOf(Color(0xFFFBBF24), Color(0xFF8CE028)),
                            rotationDegrees = -4f,
                            offsetXDp = -2f,
                            offsetYDp = 48f
                        )
                    )
                ),
                primaryCta = OnboardingCta("Next", CtaActionType.NEXT_PAGE),
                showSkip = true
            ),
            OnboardingPageConfig(
                id = "trueott_auth_landing",
                pageType = OnboardingPageType.AUTH_LANDING,
                title = "trueOTT",
                description = "LEARN • WATCH • GROW",
                artwork = OnboardingArtwork.FloatingCards(
                    cards = listOf(
                        FloatingCardItem(
                            title = "Popular\nOriginals",
                            gradientColors = listOf(Color(0xFFF43F5E), Color(0xFFC084FC)),
                            rotationDegrees = -10f,
                            offsetXDp = -52f,
                            offsetYDp = 10f,
                            showAccentBar = false
                        ),
                        FloatingCardItem(
                            title = "Tech &\nDocumentaries",
                            gradientColors = listOf(TrueBlue, TrueGreenLight),
                            rotationDegrees = 1f,
                            offsetXDp = -4f,
                            offsetYDp = -36f,
                            showAccentBar = false
                        ),
                        FloatingCardItem(
                            title = "Masterclass\nShowcases",
                            gradientColors = listOf(Color(0xFFF59E0B), Color(0xFFF43F5E)),
                            rotationDegrees = 12f,
                            offsetXDp = 50f,
                            offsetYDp = 12f,
                            showAccentBar = false
                        )
                    )
                ),
                primaryCta = OnboardingCta("Continue with Google", CtaActionType.CONTINUE_GOOGLE),
                secondaryCta = OnboardingCta("Browse as guest", CtaActionType.BROWSE_GUEST),
                showSkip = false
            )
        )
    )

    // Current active tenant state - defaults to trueOTT tenant
    private val _currentTenant = mutableStateOf(TrueOttTenant)
    val currentTenant: State<TenantConfig> = _currentTenant

    val availableTenants = listOf(
        TrueOttTenant,
        CollegeTenant,
        GymTenant,
        CoachingTenant
    )

    fun setTenant(tenantConfig: TenantConfig) {
        _currentTenant.value = tenantConfig
    }

    fun setTenantById(tenantId: String) {
        availableTenants.find { it.tenantId == tenantId }?.let {
            _currentTenant.value = it
        }
    }
}
