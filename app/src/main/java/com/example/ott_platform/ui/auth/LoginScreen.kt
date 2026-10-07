package com.example.ott_platform.ui.auth

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ott_platform.R
import com.example.ott_platform.tenant.TenantBranding
import com.example.ott_platform.tenant.TenantConfig
import com.example.ott_platform.tenant.TenantRepository
import com.example.ott_platform.ui.theme.CinemaBlack
import com.example.ott_platform.ui.theme.CinemaBorder
import com.example.ott_platform.ui.theme.CinemaDark
import com.example.ott_platform.ui.theme.CinemaSurface
import com.example.ott_platform.ui.theme.CinemaSurfaceVariant
import com.example.ott_platform.ui.theme.OTTPLATFORMTheme
import com.example.ott_platform.ui.theme.TextMuted
import com.example.ott_platform.ui.theme.TextPrimary
import com.example.ott_platform.ui.theme.TextSecondary
import com.example.ott_platform.ui.theme.TrueBlue
import com.example.ott_platform.ui.theme.TrueBlueGlow
import com.example.ott_platform.ui.theme.TrueBlueLight
import com.example.ott_platform.ui.theme.TrueGreen
import com.example.ott_platform.ui.theme.TrueGreenGlow
import com.example.ott_platform.ui.theme.TrueGreenLight

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    tenantConfig: TenantConfig = TenantRepository.TrueOttTenant,
    onLoginSuccess: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {},
    onForgotPassword: () -> Unit = {}
) {
    var emailOrUsername by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    val branding = tenantConfig.branding
    val brandColors = branding.colors

    // Ambient background pulsing glow
    val infiniteTransition = rememberInfiniteTransition(label = "authGlow")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.92f,
        targetValue = 1.22f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(brandColors.background)
    ) {
        // 1. Ambient Background Glow Orbs & Light Matrix
        AuthBackgroundGlow(pulseScale = pulseScale)

        // 2. Main Responsive Content Row (Left Form, Right Slanted Media Panel)
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            // Left Form Section
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 28.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Spacer(modifier = Modifier.height(16.dp))

                    // App Logo Header
                    AuthBrandHeader(branding = branding)

                    Spacer(modifier = Modifier.height(32.dp))

                    // Title & Subtitle
                    Text(
                        text = "Welcome Back",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Sign in to continue to your learning and entertainment.",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        color = TextSecondary,
                        lineHeight = 22.sp
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    // Input 1: Email or Username
                    OutlinedTextField(
                        value = emailOrUsername,
                        onValueChange = { emailOrUsername = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(58.dp),
                        placeholder = {
                            Text(
                                text = "Email or Username",
                                color = TextMuted,
                                fontSize = 15.sp
                            )
                        },
                        leadingIcon = {
                            AuthMailIcon(tint = TrueBlueLight)
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = CinemaSurface,
                            unfocusedContainerColor = CinemaDark,
                            focusedBorderColor = TrueBlueLight,
                            unfocusedBorderColor = CinemaBorder,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Input 2: Password
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(58.dp),
                        placeholder = {
                            Text(
                                text = "Password",
                                color = TextMuted,
                                fontSize = 15.sp
                            )
                        },
                        leadingIcon = {
                            AuthLockIcon(tint = TrueBlueLight)
                        },
                        trailingIcon = {
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .clickable { isPasswordVisible = !isPasswordVisible }
                                    .padding(8.dp)
                            ) {
                                if (isPasswordVisible) {
                                    AuthEyeIcon(tint = TextSecondary)
                                } else {
                                    AuthEyeOffIcon(tint = TextSecondary)
                                }
                            }
                        },
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = CinemaSurface,
                            unfocusedContainerColor = CinemaDark,
                            focusedBorderColor = TrueBlueLight,
                            unfocusedBorderColor = CinemaBorder,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Forgot Password Link
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "Forgot Password?",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TrueBlueLight,
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .clickable { onForgotPassword() }
                                .padding(vertical = 4.dp, horizontal = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Sign In Primary Button
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .shadow(16.dp, RoundedCornerShape(28.dp), spotColor = TrueBlueLight)
                            .clip(RoundedCornerShape(28.dp))
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(TrueBlue, TrueBlueLight)
                                )
                            )
                            .clickable { onLoginSuccess() },
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Sign In",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            // Forward Arrow Icon
                            AuthArrowRightIcon(tint = Color.White)
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // OR Divider
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(1.dp)
                                .background(CinemaBorder)
                        )
                        Text(
                            text = "OR",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextMuted,
                            modifier = Modifier.padding(horizontal = 14.dp)
                        )
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(1.dp)
                                .background(CinemaBorder)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Google Sign-In Outlined Button
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .clip(RoundedCornerShape(26.dp))
                            .background(CinemaSurface)
                            .border(1.dp, CinemaBorder, RoundedCornerShape(26.dp))
                            .clickable { onLoginSuccess() },
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(26.dp)
                                    .clip(CircleShape)
                                    .background(Color.White),
                                contentAlignment = Alignment.Center
                            ) {
                                GoogleGIcon(modifier = Modifier.size(15.dp))
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(
                                text = "Continue with Google",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        }
                    }
                }

                // Footer Link: Register
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 28.dp, bottom = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Don't have an account? ",
                            fontSize = 14.sp,
                            color = TextSecondary
                        )
                        Text(
                            text = "Sign Up",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TrueGreenLight,
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .clickable { onNavigateToRegister() }
                                .padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            // Right Slanted Media Showcase Column (3D Showcase Cards)
            AuthSlantedMediaPanel(
                modifier = Modifier
                    .weight(0.75f)
                    .fillMaxHeight()
            )
        }
    }
}

/**
 * App Logo Header displaying trueOTT emblem, name prefix/suffix, and tagline.
 */
@Composable
fun AuthBrandHeader(
    branding: TenantBranding
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (branding.logoResId != null) {
                Image(
                    painter = painterResource(id = branding.logoResId),
                    contentDescription = branding.appName,
                    modifier = Modifier.size(32.dp),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.width(10.dp))
            }

            Row {
                Text(
                    text = branding.appNamePrefix,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black,
                    color = branding.colors.brandPrefixColor
                )
                Text(
                    text = branding.appNameSuffix,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black,
                    color = branding.colors.brandSuffixColor
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Your Own Streaming Platform",
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = TextSecondary,
            letterSpacing = 0.2.sp
        )
    }
}

/**
 * Slanted media showcase card column matching the design reference layout.
 */
@Composable
private fun AuthSlantedMediaPanel(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 32.dp, bottomStart = 32.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF0F1528).copy(alpha = 0.95f),
                        Color(0xFF070A14).copy(alpha = 0.98f)
                    )
                )
            )
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.15f),
                        Color.Transparent
                    )
                ),
                shape = RoundedCornerShape(topStart = 32.dp, bottomStart = 32.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        // Tilted Grid Column
        Column(
            modifier = Modifier
                .graphicsLayer {
                    rotationZ = -8f
                    scaleX = 1.05f
                    scaleY = 1.05f
                }
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Showcase Card 1
            AuthMediaCard(
                title = "Live Lectures &\nMasterclasses",
                category = "Academics",
                gradientColors = listOf(Color(0xFF3B82F6), Color(0xFF1D4ED8))
            )

            // Showcase Card 2
            AuthMediaCard(
                title = "Live Concerts &\nCollege Fests",
                category = "Events",
                gradientColors = listOf(Color(0xFF8B5CF6), Color(0xFF6D28D9))
            )

            // Showcase Card 3
            AuthMediaCard(
                title = "Original Series &\nDocumentaries",
                category = "trueOTT Originals",
                gradientColors = listOf(Color(0xFF10B981), Color(0xFF047857))
            )

            // Showcase Card 4
            AuthMediaCard(
                title = "Shorts &\nReels Jam",
                category = "Community",
                gradientColors = listOf(Color(0xFFF59E0B), Color(0xFFD97706))
            )
        }
    }
}

/**
 * Individual showcase card in the slanted media panel.
 */
@Composable
private fun AuthMediaCard(
    title: String,
    category: String,
    gradientColors: List<Color>
) {
    Box(
        modifier = Modifier
            .width(220.dp)
            .height(150.dp)
            .shadow(12.dp, RoundedCornerShape(20.dp), spotColor = gradientColors.first())
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.linearGradient(gradientColors)
            )
            .border(1.dp, Color.White.copy(alpha = 0.25f), RoundedCornerShape(20.dp))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Black.copy(alpha = 0.35f))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = category.uppercase(),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                lineHeight = 20.sp
            )
        }
    }
}

/**
 * Background glow orbs and light grid.
 */
@Composable
fun AuthBackgroundGlow(pulseScale: Float) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Top Left Glow
        Box(
            modifier = Modifier
                .size(340.dp)
                .align(Alignment.TopStart)
                .offset(x = (-80).dp, y = (-80).dp)
                .scale(pulseScale)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            TrueBlue.copy(alpha = 0.40f),
                            TrueBlueGlow.copy(alpha = 0.15f),
                            Color.Transparent
                        )
                    )
                )
        )

        // Bottom Right Glow
        Box(
            modifier = Modifier
                .size(360.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 90.dp, y = 90.dp)
                .scale(pulseScale)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            TrueGreen.copy(alpha = 0.35f),
                            TrueGreenGlow.copy(alpha = 0.12f),
                            Color.Transparent
                        )
                    )
                )
        )
    }
}

// Canvas Vector Icon Drawings

@Composable
fun AuthMailIcon(tint: Color) {
    Canvas(modifier = Modifier.size(20.dp)) {
        val w = size.width
        val h = size.height

        // Envelope Body
        drawRoundRect(
            color = tint,
            topLeft = Offset(w * 0.08f, h * 0.2f),
            size = Size(w * 0.84f, h * 0.6f),
            cornerRadius = CornerRadius(3.dp.toPx()),
            style = Stroke(width = 2.dp.toPx())
        )

        // Envelope Flap
        val flapPath = Path().apply {
            moveTo(w * 0.08f, h * 0.22f)
            lineTo(w * 0.5f, h * 0.52f)
            lineTo(w * 0.92f, h * 0.22f)
        }
        drawPath(path = flapPath, color = tint, style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round))
    }
}

@Composable
fun AuthLockIcon(tint: Color) {
    Canvas(modifier = Modifier.size(20.dp)) {
        val w = size.width
        val h = size.height

        // Lock Body
        drawRoundRect(
            color = tint,
            topLeft = Offset(w * 0.15f, h * 0.42f),
            size = Size(w * 0.7f, h * 0.48f),
            cornerRadius = CornerRadius(3.dp.toPx()),
            style = Stroke(width = 2.dp.toPx())
        )

        // Lock Shackle
        val shacklePath = Path().apply {
            moveTo(w * 0.3f, h * 0.42f)
            lineTo(w * 0.3f, h * 0.25f)
            cubicTo(
                w * 0.3f, h * 0.10f,
                w * 0.7f, h * 0.10f,
                w * 0.7f, h * 0.25f
            )
            lineTo(w * 0.7f, h * 0.42f)
        }
        drawPath(path = shacklePath, color = tint, style = Stroke(width = 2.dp.toPx()))
    }
}

@Composable
fun AuthPersonIcon(tint: Color) {
    Canvas(modifier = Modifier.size(20.dp)) {
        val w = size.width
        val h = size.height

        // Head Circle
        drawCircle(
            color = tint,
            radius = w * 0.22f,
            center = Offset(w * 0.5f, h * 0.3f),
            style = Stroke(width = 2.dp.toPx())
        )

        // Shoulders Arc
        val shouldersPath = Path().apply {
            moveTo(w * 0.15f, h * 0.85f)
            cubicTo(
                w * 0.15f, h * 0.58f,
                w * 0.85f, h * 0.58f,
                w * 0.85f, h * 0.85f
            )
        }
        drawPath(path = shouldersPath, color = tint, style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round))
    }
}

@Composable
fun AuthPhoneIcon(tint: Color) {
    Canvas(modifier = Modifier.size(20.dp)) {
        val w = size.width
        val h = size.height

        // Phone Handset Arc
        val handsetPath = Path().apply {
            moveTo(w * 0.25f, h * 0.15f)
            lineTo(w * 0.45f, h * 0.15f)
            lineTo(w * 0.52f, h * 0.35f)
            lineTo(w * 0.38f, h * 0.48f)
            cubicTo(
                w * 0.48f, h * 0.65f,
                w * 0.65f, h * 0.78f,
                w * 0.82f, h * 0.85f
            )
            lineTo(w * 0.95f, h * 0.72f)
            lineTo(w * 1.15f, h * 0.78f)
            lineTo(w * 1.15f, h * 0.98f)
            close()
        }
        drawPath(path = handsetPath, color = tint)
    }
}

@Composable
fun AuthEyeIcon(tint: Color) {
    Canvas(modifier = Modifier.size(20.dp)) {
        val w = size.width
        val h = size.height

        val eyePath = Path().apply {
            moveTo(w * 0.1f, h * 0.5f)
            quadraticBezierTo(w * 0.5f, h * 0.15f, w * 0.9f, h * 0.5f)
            quadraticBezierTo(w * 0.5f, h * 0.85f, w * 0.1f, h * 0.5f)
            close()
        }
        drawPath(path = eyePath, color = tint, style = Stroke(width = 2.dp.toPx()))
        drawCircle(color = tint, radius = w * 0.15f, center = Offset(w * 0.5f, h * 0.5f))
    }
}

@Composable
fun AuthEyeOffIcon(tint: Color) {
    Canvas(modifier = Modifier.size(20.dp)) {
        val w = size.width
        val h = size.height

        val eyePath = Path().apply {
            moveTo(w * 0.1f, h * 0.5f)
            quadraticBezierTo(w * 0.5f, h * 0.15f, w * 0.9f, h * 0.5f)
            quadraticBezierTo(w * 0.5f, h * 0.85f, w * 0.1f, h * 0.5f)
            close()
        }
        drawPath(path = eyePath, color = tint, style = Stroke(width = 2.dp.toPx()))
        drawLine(
            color = tint,
            start = Offset(w * 0.15f, h * 0.85f),
            end = Offset(w * 0.85f, h * 0.15f),
            strokeWidth = 2.dp.toPx(),
            cap = StrokeCap.Round
        )
    }
}

@Composable
fun AuthArrowRightIcon(tint: Color) {
    Canvas(modifier = Modifier.size(18.dp)) {
        val w = size.width
        val h = size.height
        val strokeWidth = 2.5.dp.toPx()

        drawLine(
            color = tint,
            start = Offset(2.dp.toPx(), h / 2),
            end = Offset(w - 2.dp.toPx(), h / 2),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
        drawLine(
            color = tint,
            start = Offset(w - 7.dp.toPx(), 3.dp.toPx()),
            end = Offset(w - 2.dp.toPx(), h / 2),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
        drawLine(
            color = tint,
            start = Offset(w - 7.dp.toPx(), h - 3.dp.toPx()),
            end = Offset(w - 2.dp.toPx(), h / 2),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
    }
}

@Composable
fun AuthBackArrowIcon(tint: Color) {
    Canvas(modifier = Modifier.size(20.dp)) {
        val w = size.width
        val h = size.height
        val strokeWidth = 2.5.dp.toPx()

        drawLine(
            color = tint,
            start = Offset(w * 0.65f, h * 0.15f),
            end = Offset(w * 0.25f, h * 0.5f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
        drawLine(
            color = tint,
            start = Offset(w * 0.65f, h * 0.85f),
            end = Offset(w * 0.25f, h * 0.5f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
    }
}

@Composable
fun GoogleGIcon(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val strokeWidth = w * 0.22f

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
        drawArc(
            color = Color(0xFFEA4335),
            startAngle = -180f,
            sweepAngle = 135f,
            useCenter = false,
            style = Stroke(width = strokeWidth)
        )
        drawArc(
            color = Color(0xFFFBBC05),
            startAngle = 135f,
            sweepAngle = 90f,
            useCenter = false,
            style = Stroke(width = strokeWidth)
        )
        drawArc(
            color = Color(0xFF34A853),
            startAngle = 45f,
            sweepAngle = 90f,
            useCenter = false,
            style = Stroke(width = strokeWidth)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF07090E, widthDp = 1000, heightDp = 800)
@Composable
fun LoginScreenPreview() {
    OTTPLATFORMTheme {
        LoginScreen()
    }
}
