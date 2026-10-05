package com.example.ott_platform.ui.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ott_platform.R
import com.example.ott_platform.ui.theme.CinemaBlack
import com.example.ott_platform.ui.theme.CinemaDark
import com.example.ott_platform.ui.theme.OTTPLATFORMTheme
import com.example.ott_platform.ui.theme.TextMuted
import com.example.ott_platform.ui.theme.TextPrimary
import com.example.ott_platform.ui.theme.TextSecondary
import com.example.ott_platform.ui.theme.TrueBlue
import com.example.ott_platform.ui.theme.TrueBlueGlow
import com.example.ott_platform.ui.theme.TrueBlueLight
import com.example.ott_platform.ui.theme.TrueBlueNebula
import com.example.ott_platform.ui.theme.TrueGreen
import com.example.ott_platform.ui.theme.TrueGreenGlow
import com.example.ott_platform.ui.theme.TrueGreenLight
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    onSplashFinished: () -> Unit = {}
) {
    // Animation controllers
    val logoScale = remember { Animatable(0.4f) }
    val logoAlpha = remember { Animatable(0f) }
    val titleAlpha = remember { Animatable(0f) }
    val titleOffsetY = remember { Animatable(40f) }
    val subtitleAlpha = remember { Animatable(0f) }
    val progressAnim = remember { Animatable(0f) }
    val footerAlpha = remember { Animatable(0f) }

    // Ambient pulsing glow transition
    val infiniteTransition = rememberInfiniteTransition(label = "ambientGlow")
    val ambientPulseScale by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "ambientPulseScale"
    )
    val ambientPulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.35f,
        targetValue = 0.70f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "ambientPulseAlpha"
    )

    // Shimmer sweep across logo card
    val shimmerOffset by infiniteTransition.animateFloat(
        initialValue = -1.2f,
        targetValue = 2.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(2400, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerOffset"
    )

    // Launch choreographed entrance sequence
    LaunchedEffect(Unit) {
        // 1. Logo pop-in with spring
        launch {
            logoAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
            )
        }
        launch {
            logoScale.animateTo(
                targetValue = 1.05f,
                animationSpec = spring(dampingRatio = 0.6f, stiffness = 400f)
            )
            logoScale.animateTo(
                targetValue = 1.0f,
                animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing)
            )
        }

        delay(350)

        // 2. Title slide up and reveal
        launch {
            titleAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
            )
        }
        launch {
            titleOffsetY.animateTo(
                targetValue = 0f,
                animationSpec = spring(dampingRatio = 0.75f, stiffness = 500f)
            )
        }

        delay(250)

        // 3. Tagline & Footer reveal
        launch {
            subtitleAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 400)
            )
            footerAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 500)
            )
        }

        // 4. Progress bar fill
        launch {
            progressAnim.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 1600, easing = FastOutSlowInEasing)
            )
        }

        // 5. Finished delay & notify
        delay(1900)
        onSplashFinished()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        TrueBlueNebula, // Deep cinematic oceanic blue glow at center
                        Color(0xFF09111D),
                        CinemaBlack
                    ),
                    center = Offset(Float.POSITIVE_INFINITY / 2, Float.POSITIVE_INFINITY / 2),
                    radius = 1400f
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Dynamic Ambient Light Aura in trueOTT Blue & Green
        Box(
            modifier = Modifier
                .size(330.dp)
                .scale(ambientPulseScale)
                .alpha(ambientPulseAlpha)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            TrueBlueGlow.copy(alpha = 0.38f),
                            TrueGreenGlow.copy(alpha = 0.22f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )

        // Central Content Column
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Animated Brand Logo Emblem
            Box(
                modifier = Modifier
                    .scale(logoScale.value)
                    .alpha(logoAlpha.value),
                contentAlignment = Alignment.Center
            ) {
                // Outer Glow Ring with Blue & Green Spotlights
                Box(
                    modifier = Modifier
                        .size(width = 152.dp, height = 120.dp)
                        .shadow(
                            elevation = 36.dp,
                            shape = RoundedCornerShape(32.dp),
                            ambientColor = TrueBlueGlow,
                            spotColor = TrueGreenGlow
                        )
                )

                // Main Logo Card Surface
                Box(
                    modifier = Modifier
                        .size(width = 144.dp, height = 114.dp)
                        .clip(RoundedCornerShape(30.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF101B2C),
                                    Color(0xFF0B1420),
                                    Color(0xFF060A10)
                                )
                            )
                        )
                        .border(
                            width = 1.5.dp,
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    TrueBlueGlow.copy(alpha = 0.85f),
                                    Color.White.copy(alpha = 0.25f),
                                    TrueGreenGlow.copy(alpha = 0.85f)
                                )
                            ),
                            shape = RoundedCornerShape(30.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    // Logo Image provided by user (stylized "1" + play button)
                    Image(
                        painter = painterResource(id = R.drawable.trueott_emblem),
                        contentDescription = "trueOTT Logo",
                        modifier = Modifier
                            .size(width = 98.dp, height = 58.dp)
                            .padding(horizontal = 4.dp),
                        contentScale = ContentScale.Fit
                    )

                    // Shimmer Light Reflection Sweep
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.White.copy(alpha = 0.16f),
                                        Color.Transparent
                                    ),
                                    startX = shimmerOffset * 250f,
                                    endX = (shimmerOffset + 0.6f) * 250f
                                )
                            )
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Brand Title: 'trueOTT' with Distinctive Dual-Tone Styling
            Column(
                modifier = Modifier
                    .offset { IntOffset(0, titleOffsetY.value.toInt()) }
                    .alpha(titleAlpha.value),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "true",
                        color = TrueBlueLight,
                        fontSize = 38.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "OTT",
                        color = TrueGreenLight,
                        fontSize = 38.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 2.sp,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Tagline / Slogan: learn watch grow
                Text(
                    text = "LEARN • WATCH • GROW",
                    modifier = Modifier.alpha(subtitleAlpha.value),
                    color = TrueGreenLight.copy(alpha = 0.95f),
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 3.5.sp,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(52.dp))

            // Cinematic Glowing Progress Bar in trueOTT Gradient
            Column(
                modifier = Modifier
                    .width(180.dp)
                    .alpha(subtitleAlpha.value),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.5.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(Color(0xFF141F30))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(fraction = progressAnim.value)
                            .height(3.5.dp)
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        TrueBlue,
                                        TrueBlueLight,
                                        TrueGreenLight
                                    )
                                )
                            )
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "INITIALIZING EXPERIENCE",
                    color = TextMuted,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 2.sp
                )
            }
        }

        // Bottom Cinema Badges (4K ULTRA HD, DOLBY ATMOS, HDR10+)
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 36.dp)
                .alpha(footerAlpha.value),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BadgePill(text = "4K ULTRA HD")
            BadgeDot()
            BadgePill(text = "DOLBY ATMOS")
            BadgeDot()
            BadgePill(text = "HDR10+")
        }
    }
}

@Composable
private fun BadgePill(text: String) {
    Box(
        modifier = Modifier
            .background(
                color = CinemaDark.copy(alpha = 0.7f),
                shape = RoundedCornerShape(6.dp)
            )
            .border(
                width = 0.8.dp,
                color = Color.White.copy(alpha = 0.12f),
                shape = RoundedCornerShape(6.dp)
            )
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(
            text = text,
            color = TextSecondary,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )
    }
}

@Composable
private fun BadgeDot() {
    Box(
        modifier = Modifier
            .size(3.dp)
            .background(TrueGreenLight.copy(alpha = 0.5f), shape = CircleShape)
    )
}

@Preview(name = "trueOTT Splash Screen Preview", showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenPreview() {
    OTTPLATFORMTheme {
        SplashScreen()
    }
}
