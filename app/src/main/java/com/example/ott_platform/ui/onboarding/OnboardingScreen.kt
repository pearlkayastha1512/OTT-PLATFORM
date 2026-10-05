package com.example.ott_platform.ui.onboarding

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ott_platform.tenant.CardIconType
import com.example.ott_platform.tenant.FloatingCardItem
import com.example.ott_platform.tenant.OnboardingArtwork
import com.example.ott_platform.tenant.OnboardingPageConfig
import com.example.ott_platform.tenant.OnboardingPageType
import com.example.ott_platform.tenant.TenantColors
import com.example.ott_platform.tenant.TenantConfig
import com.example.ott_platform.tenant.TenantRepository
import com.example.ott_platform.ui.theme.OTTPLATFORMTheme
import com.example.ott_platform.ui.theme.TrueBlue
import com.example.ott_platform.ui.theme.TrueBlueDark
import com.example.ott_platform.ui.theme.TrueBlueGlow
import com.example.ott_platform.ui.theme.TrueBlueLight
import com.example.ott_platform.ui.theme.TrueGreen
import com.example.ott_platform.ui.theme.TrueGreenDark
import com.example.ott_platform.ui.theme.TrueGreenGlow
import com.example.ott_platform.ui.theme.TrueGreenLight
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    tenantConfig: TenantConfig = TenantRepository.TrueOttTenant,
    onFinishOnboarding: () -> Unit = {}
) {
    val pages = tenantConfig.onboardingPages
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val coroutineScope = rememberCoroutineScope()
    val brandColors = tenantConfig.branding.colors

    // Ambient pulsing glow transition
    val infiniteTransition = rememberInfiniteTransition(label = "ambientGlow")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(2800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(brandColors.background)
    ) {
        // 1. Rich Cinematic Multi-Layered Background (Matrix Grid + Glow Orbs + Concentric Rings + Light Waves + Bokeh)
        RichCinematicBackground(
            pulseScale = pulseScale
        )

        // 2. Main Onboarding Pager View
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top Bar with Logo & Skip
            val currentPageConfig = pages.getOrNull(pagerState.currentPage)
            OnboardingTopBar(
                branding = tenantConfig.branding,
                showSkip = currentPageConfig?.showSkip == true,
                onSkip = {
                    if (pagerState.currentPage < pages.size - 1) {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pages.size - 1)
                        }
                    } else {
                        onFinishOnboarding()
                    }
                }
            )

            // Pager Content
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) { pageIndex ->
                val pageConfig = pages[pageIndex]
                OnboardingPageContent(
                    pageConfig = pageConfig,
                    branding = tenantConfig.branding,
                    onFinish = onFinishOnboarding
                )
            }

            // Bottom Navigation Footer for Feature Highlight Pages
            if (currentPageConfig?.pageType == OnboardingPageType.FEATURE_HIGHLIGHT) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 28.dp, vertical = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Page Indicator Dots
                    PageIndicator(
                        pageCount = pages.size,
                        currentPage = pagerState.currentPage,
                        activeColor = Color.White,
                        inactiveColor = Color.White.copy(alpha = 0.3f)
                    )

                    // Next Page Circular CTA Button
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .shadow(16.dp, CircleShape, spotColor = brandColors.primary)
                            .clip(CircleShape)
                            .background(brandColors.primary)
                            .clickable {
                                if (pagerState.currentPage < pages.size - 1) {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                    }
                                } else {
                                    onFinishOnboarding()
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        // Forward Arrow Icon
                        Canvas(modifier = Modifier.size(22.dp)) {
                            val strokeWidth = 3.dp.toPx()
                            // Arrow shaft
                            drawLine(
                                color = brandColors.onPrimary,
                                start = Offset(2.dp.toPx(), size.height / 2),
                                end = Offset(size.width - 2.dp.toPx(), size.height / 2),
                                strokeWidth = strokeWidth,
                                cap = StrokeCap.Round
                            )
                            // Arrow head upper
                            drawLine(
                                color = brandColors.onPrimary,
                                start = Offset(size.width - 9.dp.toPx(), 4.dp.toPx()),
                                end = Offset(size.width - 2.dp.toPx(), size.height / 2),
                                strokeWidth = strokeWidth,
                                cap = StrokeCap.Round
                            )
                            // Arrow head lower
                            drawLine(
                                color = brandColors.onPrimary,
                                start = Offset(size.width - 9.dp.toPx(), size.height - 4.dp.toPx()),
                                end = Offset(size.width - 2.dp.toPx(), size.height / 2),
                                strokeWidth = strokeWidth,
                                cap = StrokeCap.Round
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Rich Cinematic Background layer featuring:
 * - Tech Matrix Grid Dots
 * - Glowing Ambient Radial Orbs
 * - Concentric Dashed Accent Rings around the artwork focal center
 * - Flowing Light Waves bridging top and bottom content
 * - Floating Bokeh Sparkle Particles
 */
@Composable
private fun RichCinematicBackground(
    pulseScale: Float
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // A. Radial Glow Orbs
        // Top Right Blue Glow Orb
        Box(
            modifier = Modifier
                .size(360.dp)
                .align(Alignment.TopEnd)
                .offset(x = 90.dp, y = (-70).dp)
                .scale(pulseScale)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            TrueBlue.copy(alpha = 0.50f),
                            TrueBlueGlow.copy(alpha = 0.22f),
                            Color.Transparent
                        )
                    )
                )
        )

        // Bottom Left Lime Green Glow Orb
        Box(
            modifier = Modifier
                .size(380.dp)
                .align(Alignment.BottomStart)
                .offset(x = (-110).dp, y = 90.dp)
                .scale(pulseScale)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            TrueGreen.copy(alpha = 0.40f),
                            TrueGreenGlow.copy(alpha = 0.18f),
                            Color.Transparent
                        )
                    )
                )
        )

        // Center Purple-Cyan Glowing Nebula Blob
        Box(
            modifier = Modifier
                .size(320.dp)
                .align(Alignment.Center)
                .offset(y = (-30).dp)
                .scale(1.15f - pulseScale * 0.1f)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF8B5CF6).copy(alpha = 0.32f),
                            Color(0xFF3B82F6).copy(alpha = 0.15f),
                            Color.Transparent
                        )
                    )
                )
        )

        // B. Tech Matrix Grid + Concentric Rings Canvas
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            // 1. Grid Dot Matrix (Fills empty areas with tech grid texture)
            val gridSpacing = 44.dp.toPx()
            val dotRadius = 1.6.dp.toPx()
            val cols = (w / gridSpacing).toInt() + 1
            val rows = (h / gridSpacing).toInt() + 1

            for (i in 0..cols) {
                for (j in 0..rows) {
                    val x = i * gridSpacing
                    val y = j * gridSpacing
                    val distFromCenter = Offset(x - w / 2, y - h / 2).getDistance()
                    val alpha = (0.12f - (distFromCenter / (w * 1.5f))).coerceIn(0.02f, 0.09f)

                    drawCircle(
                        color = Color.White.copy(alpha = alpha),
                        radius = dotRadius,
                        center = Offset(x, y)
                    )
                }
            }

            // 2. Concentric Glowing Accent Rings around center 3D artwork stack
            val centerX = w * 0.5f
            val centerY = h * 0.35f

            // Inner Ring
            drawCircle(
                color = TrueBlueLight.copy(alpha = 0.14f),
                radius = w * 0.38f,
                center = Offset(centerX, centerY),
                style = Stroke(
                    width = 1.5.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 15f), 0f)
                )
            )

            // Outer Ring
            drawCircle(
                color = TrueGreenLight.copy(alpha = 0.10f),
                radius = w * 0.52f,
                center = Offset(centerX, centerY),
                style = Stroke(
                    width = 2.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(30f, 20f), 0f)
                )
            )

            // 3. Flowing Light Waves (Eliminates vacant vertical spaces)
            val wavePath1 = Path().apply {
                moveTo(0f, h * 0.22f)
                cubicTo(
                    w * 0.3f, h * 0.18f,
                    w * 0.7f, h * 0.26f,
                    w, h * 0.20f
                )
            }
            drawPath(
                path = wavePath1,
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        TrueBlue.copy(alpha = 0.35f),
                        TrueGreen.copy(alpha = 0.28f),
                        Color.Transparent
                    )
                ),
                style = Stroke(width = 2.dp.toPx())
            )

            val wavePath2 = Path().apply {
                moveTo(0f, h * 0.68f)
                cubicTo(
                    w * 0.35f, h * 0.74f,
                    w * 0.65f, h * 0.64f,
                    w, h * 0.70f
                )
            }
            drawPath(
                path = wavePath2,
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color.Transparent,
                        TrueGreen.copy(alpha = 0.28f),
                        TrueBlueLight.copy(alpha = 0.22f)
                    )
                ),
                style = Stroke(width = 1.8.dp.toPx())
            )

            // 4. Scattered Floating Bokeh Sparkle Particles
            val particleOffsets = listOf(
                Offset(w * 0.12f, h * 0.18f) to 2.5.dp.toPx(),
                Offset(w * 0.88f, h * 0.25f) to 3.5.dp.toPx(),
                Offset(w * 0.20f, h * 0.52f) to 2.0.dp.toPx(),
                Offset(w * 0.82f, h * 0.62f) to 3.0.dp.toPx(),
                Offset(w * 0.15f, h * 0.78f) to 2.2.dp.toPx(),
                Offset(w * 0.85f, h * 0.82f) to 2.8.dp.toPx(),
                Offset(w * 0.50f, h * 0.90f) to 3.2.dp.toPx()
            )

            particleOffsets.forEach { (pos, r) ->
                drawCircle(
                    color = Color.White.copy(alpha = 0.35f),
                    radius = r,
                    center = pos
                )
            }
        }
    }
}

/**
 * Top App Bar displaying tenant branding / logo and optional "Skip" button.
 */
@Composable
private fun OnboardingTopBar(
    branding: com.example.ott_platform.tenant.TenantBranding,
    showSkip: Boolean,
    onSkip: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // App Brand Logo (Text or Emblem)
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (branding.logoResId != null) {
                Image(
                    painter = painterResource(id = branding.logoResId),
                    contentDescription = branding.appName,
                    modifier = Modifier.size(28.dp),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.width(8.dp))
            }

            // Styled Name Prefix + Suffix
            Row {
                Text(
                    text = branding.appNamePrefix,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = branding.colors.brandPrefixColor
                )
                Text(
                    text = branding.appNameSuffix,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = branding.colors.brandSuffixColor
                )
            }
        }

        // Skip Button
        if (showSkip) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onSkip() }
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "Skip",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        } else {
            Spacer(modifier = Modifier.width(48.dp))
        }
    }
}

/**
 * Renders the content of a single onboarding page.
 */
@Composable
private fun OnboardingPageContent(
    pageConfig: OnboardingPageConfig,
    branding: com.example.ott_platform.tenant.TenantBranding,
    onFinish: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        when (pageConfig.pageType) {
            OnboardingPageType.FEATURE_HIGHLIGHT -> {
                // 1. Top Artwork Area (Floating 3D Cards Stack + Depth Cards)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1.1f),
                    contentAlignment = Alignment.Center
                ) {
                    if (pageConfig.artwork is OnboardingArtwork.FloatingCards) {
                        FloatingCardsArtworkStack(artwork = pageConfig.artwork)
                    }
                }

                // 2. Text Content Area
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = pageConfig.title,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        lineHeight = 36.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = pageConfig.description,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        color = branding.colors.textSecondary,
                        textAlign = TextAlign.Center,
                        lineHeight = 22.sp,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
            }

            OnboardingPageType.AUTH_LANDING, OnboardingPageType.SHOWCASE_CARDS -> {
                // 1. Top Stack Artwork
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.9f),
                    contentAlignment = Alignment.Center
                ) {
                    if (pageConfig.artwork is OnboardingArtwork.FloatingCards) {
                        FloatingCardsArtworkStack(
                            artwork = pageConfig.artwork,
                            isAuthLanding = true
                        )
                    }
                }

                // 2. Center Branding Header
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = branding.appNamePrefix,
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Black,
                            color = branding.colors.brandPrefixColor
                        )
                        Text(
                            text = branding.appNameSuffix,
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Black,
                            color = branding.colors.brandSuffixColor
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = branding.tagline,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White.copy(alpha = 0.9f),
                        letterSpacing = 0.5.sp
                    )
                }

                // 3. Auth Actions Area
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Google Sign-In Button
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .shadow(16.dp, RoundedCornerShape(28.dp), spotColor = branding.colors.primary)
                            .clip(RoundedCornerShape(28.dp))
                            .background(branding.colors.primary)
                            .clickable { onFinish() },
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            // Google 'G' Icon Badge
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(Color.White),
                                contentAlignment = Alignment.Center
                            ) {
                                GoogleGIcon(modifier = Modifier.size(16.dp))
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(
                                text = pageConfig.primaryCta?.text ?: "Continue with Google",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = branding.colors.onPrimary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // OR Divider Line
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(1.dp)
                                .background(Color.White.copy(alpha = 0.15f))
                        )
                        Text(
                            text = "OR",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = branding.colors.textSecondary,
                            modifier = Modifier.padding(horizontal = 14.dp)
                        )
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(1.dp)
                                .background(Color.White.copy(alpha = 0.15f))
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Browse as Guest Button
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { onFinish() }
                            .padding(horizontal = 24.dp, vertical = 10.dp)
                    ) {
                        Text(
                            text = pageConfig.secondaryCta?.text ?: "Browse as guest",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Guest Description
                    Text(
                        text = "Guests can watch trueOTT Originals, free premieres, and community showcases.",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal,
                        color = branding.colors.textSecondary,
                        textAlign = TextAlign.Center,
                        lineHeight = 18.sp,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                }
            }
        }
    }
}

/**
 * Component rendering stacked 3D floating glassmorphic cards with deeper background depth-of-field cards.
 */
@Composable
private fun FloatingCardsArtworkStack(
    artwork: OnboardingArtwork.FloatingCards,
    isAuthLanding: Boolean = false
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        // Deep background card 1 (Far Left Depth Card)
        FloatingCardView(
            card = FloatingCardItem(
                title = "4K HDR",
                gradientColors = listOf(TrueBlueDark.copy(alpha = 0.40f), TrueBlue.copy(alpha = 0.20f)),
                rotationDegrees = -22f,
                offsetXDp = -92f,
                offsetYDp = -36f,
                showAccentBar = false
            ),
            isAuthLanding = isAuthLanding,
            isDepthCard = true
        )

        // Deep background card 2 (Far Right Depth Card)
        FloatingCardView(
            card = FloatingCardItem(
                title = "Live TV",
                gradientColors = listOf(TrueGreenDark.copy(alpha = 0.40f), TrueGreen.copy(alpha = 0.20f)),
                rotationDegrees = 20f,
                offsetXDp = 90f,
                offsetYDp = -28f,
                showAccentBar = false
            ),
            isAuthLanding = isAuthLanding,
            isDepthCard = true
        )

        // Main Foreground Floating Cards
        artwork.cards.forEach { cardItem ->
            FloatingCardView(
                card = cardItem,
                isAuthLanding = isAuthLanding
            )
        }
    }
}

/**
 * Individual Floating Glassmorphism Card with 3D rotation, gradients, and icon.
 */
@Composable
private fun FloatingCardView(
    card: FloatingCardItem,
    isAuthLanding: Boolean,
    isDepthCard: Boolean = false
) {
    val cardWidth = when {
        isDepthCard -> 118.dp
        isAuthLanding -> 140.dp
        else -> 165.dp
    }
    val cardHeight = when {
        isDepthCard -> 152.dp
        isAuthLanding -> 185.dp
        else -> 205.dp
    }

    Box(
        modifier = Modifier
            .offset(x = card.offsetXDp.dp, y = card.offsetYDp.dp)
            .graphicsLayer {
                rotationZ = card.rotationDegrees
                cameraDistance = 12f * density
            }
            .alpha(if (isDepthCard) 0.35f else 1f)
            .width(cardWidth)
            .height(cardHeight)
            .shadow(
                elevation = if (isDepthCard) 6.dp else 20.dp,
                shape = RoundedCornerShape(22.dp),
                spotColor = card.gradientColors.firstOrNull() ?: TrueBlue
            )
            .clip(RoundedCornerShape(22.dp))
            .background(
                Brush.linearGradient(
                    colors = card.gradientColors,
                    start = Offset(0f, 0f),
                    end = Offset(300f, 400f)
                )
            )
            .border(
                width = 1.2.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.White.copy(alpha = if (isDepthCard) 0.25f else 0.5f),
                        Color.White.copy(alpha = 0.1f)
                    )
                ),
                shape = RoundedCornerShape(22.dp)
            )
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Right Glass Icon (if icon type is configured)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                if (card.iconType != null) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.20f)),
                        contentAlignment = Alignment.Center
                    ) {
                        CardIconGraphic(
                            iconType = card.iconType,
                            tint = Color.White
                        )
                    }
                }
            }

            // Bottom Section: Title + Accent Bar
            Column {
                Text(
                    text = card.title,
                    fontSize = when {
                        isDepthCard -> 14.sp
                        isAuthLanding -> 17.sp
                        else -> 18.sp
                    },
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    lineHeight = 22.sp
                )

                if (card.showAccentBar) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .width(36.dp)
                            .height(5.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.45f))
                    )
                }
            }
        }
    }
}

/**
 * Canvas graphics for floating card icons (Academic Cap, Live Play, Clapperboard, etc.).
 */
@Composable
private fun CardIconGraphic(
    iconType: CardIconType,
    tint: Color
) {
    Canvas(modifier = Modifier.size(20.dp)) {
        val w = size.width
        val h = size.height

        when (iconType) {
            CardIconType.ACADEMIC_CAP -> {
                // Mortarboard Diamond Top
                val capPath = Path().apply {
                    moveTo(w * 0.5f, h * 0.15f)
                    lineTo(w * 0.95f, h * 0.4f)
                    lineTo(w * 0.5f, h * 0.65f)
                    lineTo(w * 0.05f, h * 0.4f)
                    close()
                }
                drawPath(path = capPath, color = tint)

                // Cap Base Arc
                val baseCapPath = Path().apply {
                    moveTo(w * 0.25f, h * 0.52f)
                    cubicTo(
                        w * 0.25f, h * 0.8f,
                        w * 0.75f, h * 0.8f,
                        w * 0.75f, h * 0.52f
                    )
                }
                drawPath(
                    path = baseCapPath,
                    color = tint,
                    style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
                )
            }

            CardIconType.LIVE_EVENTS -> {
                // Play Triangle Icon
                val playPath = Path().apply {
                    moveTo(w * 0.3f, h * 0.2f)
                    lineTo(w * 0.82f, h * 0.5f)
                    lineTo(w * 0.3f, h * 0.8f)
                    close()
                }
                drawPath(path = playPath, color = tint)
            }

            CardIconType.CLAPPERBOARD -> {
                // Film Clapper Board
                drawRoundRect(
                    color = tint,
                    topLeft = Offset(w * 0.1f, h * 0.35f),
                    size = Size(w * 0.8f, h * 0.55f),
                    cornerRadius = CornerRadius(3.dp.toPx())
                )
                // Top Clapper Strip
                drawLine(
                    color = tint,
                    start = Offset(w * 0.1f, h * 0.2f),
                    end = Offset(w * 0.9f, h * 0.2f),
                    strokeWidth = 3.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }

            CardIconType.DUMBBELL, CardIconType.TIMER, CardIconType.SPARKLES, CardIconType.BOOK_OPEN -> {
                // Sparkle / Star polygon default
                val starPath = Path().apply {
                    moveTo(w * 0.5f, h * 0.1f)
                    lineTo(w * 0.62f, h * 0.38f)
                    lineTo(w * 0.9f, h * 0.5f)
                    lineTo(w * 0.62f, h * 0.62f)
                    lineTo(w * 0.5f, h * 0.9f)
                    lineTo(w * 0.38f, h * 0.62f)
                    lineTo(w * 0.1f, h * 0.5f)
                    lineTo(w * 0.38f, h * 0.38f)
                    close()
                }
                drawPath(path = starPath, color = tint)
            }
        }
    }
}

/**
 * Authentic 4-color Google 'G' emblem rendered via Canvas.
 */
@Composable
private fun GoogleGIcon(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val strokeWidth = w * 0.22f

        // Blue Right Arc & Horizontal Bar
        drawArc(
            color = Color(0xFF4285F4),
            startAngle = -45f,
            sweepAngle = 90f,
            useCenter = false,
            style = Stroke(width = strokeWidth)
        )
        drawLine(
            color = Color(0xFF4285F4),
            start = Offset(w * 0.5f, h * 0.5f),
            end = Offset(w * 0.95f, h * 0.5f),
            strokeWidth = strokeWidth
        )

        // Red Top Arc
        drawArc(
            color = Color(0xFFEA4335),
            startAngle = -180f,
            sweepAngle = 135f,
            useCenter = false,
            style = Stroke(width = strokeWidth)
        )

        // Yellow Left Arc
        drawArc(
            color = Color(0xFFFBBC05),
            startAngle = 135f,
            sweepAngle = 90f,
            useCenter = false,
            style = Stroke(width = strokeWidth)
        )

        // Green Bottom Arc
        drawArc(
            color = Color(0xFF34A853),
            startAngle = 45f,
            sweepAngle = 90f,
            useCenter = false,
            style = Stroke(width = strokeWidth)
        )
    }
}

/**
 * Animated row of page indicator dots.
 */
@Composable
private fun PageIndicator(
    pageCount: Int,
    currentPage: Int,
    activeColor: Color,
    inactiveColor: Color
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) { index ->
            val isActive = index == currentPage
            Box(
                modifier = Modifier
                    .height(8.dp)
                    .width(if (isActive) 24.dp else 8.dp)
                    .clip(CircleShape)
                    .background(if (isActive) activeColor else inactiveColor)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF07090E)
@Composable
fun OnboardingScreenPreview() {
    OTTPLATFORMTheme {
        OnboardingScreen()
    }
}
