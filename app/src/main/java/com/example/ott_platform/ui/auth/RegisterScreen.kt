package com.example.ott_platform.ui.auth

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ott_platform.tenant.TenantConfig
import com.example.ott_platform.tenant.TenantRepository
import com.example.ott_platform.ui.theme.CinemaBorder
import com.example.ott_platform.ui.theme.CinemaDark
import com.example.ott_platform.ui.theme.CinemaSurface
import com.example.ott_platform.ui.theme.OTTPLATFORMTheme
import com.example.ott_platform.ui.theme.TextMuted
import com.example.ott_platform.ui.theme.TextSecondary
import com.example.ott_platform.ui.theme.TrueBlue
import com.example.ott_platform.ui.theme.TrueBlueLight
import com.example.ott_platform.ui.theme.TrueGreen
import com.example.ott_platform.ui.theme.TrueGreenLight

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    tenantConfig: TenantConfig = TenantRepository.TrueOttTenant,
    onRegisterSuccess: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }
    var agreedToTerms by remember { mutableStateOf(true) }

    val branding = tenantConfig.branding
    val brandColors = branding.colors

    // Ambient background pulsing glow
    val infiniteTransition = rememberInfiniteTransition(label = "registerGlow")
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
        // 1. Ambient Background Glow Orbs
        AuthBackgroundGlow(pulseScale = pulseScale)

        // 2. Main Content Row
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            // Left Form Section
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 28.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    // Top Navigation Header (Back Button & Sign In Link)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .clickable { onBackClick() }
                                .padding(8.dp)
                        ) {
                            AuthBackArrowIcon(tint = Color.White)
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Already have an account? ",
                                fontSize = 13.sp,
                                color = TextSecondary
                            )
                            Text(
                                text = "Sign In",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = TrueBlueLight,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .clickable { onNavigateToLogin() }
                                    .padding(horizontal = 4.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // App Logo Header
                    AuthBrandHeader(branding = branding)

                    Spacer(modifier = Modifier.height(24.dp))

                    // Titles
                    Text(
                        text = "Create Your Account",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Join trueOTT to access amazing content and learning resources.",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = TextSecondary,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Field 1: Full Name
                    OutlinedTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        placeholder = { Text(text = "Full Name", color = TextMuted, fontSize = 14.sp) },
                        leadingIcon = { AuthPersonIcon(tint = TrueBlueLight) },
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

                    Spacer(modifier = Modifier.height(14.dp))

                    // Field 2: Email Address
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        placeholder = { Text(text = "Email Address", color = TextMuted, fontSize = 14.sp) },
                        leadingIcon = { AuthMailIcon(tint = TrueBlueLight) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
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

                    Spacer(modifier = Modifier.height(14.dp))

                    // Field 3: Mobile Number
                    OutlinedTextField(
                        value = mobileNumber,
                        onValueChange = { mobileNumber = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        placeholder = { Text(text = "Mobile Number", color = TextMuted, fontSize = 14.sp) },
                        leadingIcon = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(start = 12.dp, end = 4.dp)
                            ) {
                                AuthPhoneIcon(tint = TrueBlueLight)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "+91 ▾",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Box(
                                    modifier = Modifier
                                        .width(1.dp)
                                        .height(20.dp)
                                        .background(CinemaBorder)
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
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

                    Spacer(modifier = Modifier.height(14.dp))

                    // Field 4: Password
                    Column {
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            placeholder = { Text(text = "Password", color = TextMuted, fontSize = 14.sp) },
                            leadingIcon = { AuthLockIcon(tint = TrueBlueLight) },
                            trailingIcon = {
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .clickable { isPasswordVisible = !isPasswordVisible }
                                        .padding(8.dp)
                                ) {
                                    if (isPasswordVisible) AuthEyeIcon(tint = TextSecondary) else AuthEyeOffIcon(tint = TextSecondary)
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

                        Text(
                            text = "Use at least 8 characters with a mix of letters, numbers and symbols.",
                            fontSize = 11.sp,
                            color = TextMuted,
                            modifier = Modifier.padding(start = 6.dp, top = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Field 5: Confirm Password
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        placeholder = { Text(text = "Confirm Password", color = TextMuted, fontSize = 14.sp) },
                        leadingIcon = { AuthLockIcon(tint = TrueBlueLight) },
                        trailingIcon = {
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .clickable { isConfirmPasswordVisible = !isConfirmPasswordVisible }
                                    .padding(8.dp)
                            ) {
                                if (isConfirmPasswordVisible) AuthEyeIcon(tint = TextSecondary) else AuthEyeOffIcon(tint = TextSecondary)
                            }
                        },
                        visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
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

                    Spacer(modifier = Modifier.height(16.dp))

                    // Terms & Privacy Checkbox
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 2.dp)
                    ) {
                        Checkbox(
                            checked = agreedToTerms,
                            onCheckedChange = { agreedToTerms = it },
                            colors = CheckboxDefaults.colors(
                                checkedColor = TrueGreenLight,
                                uncheckedColor = TextMuted,
                                checkmarkColor = Color.Black
                            )
                        )

                        Text(
                            text = "I agree to the ",
                            fontSize = 13.sp,
                            color = TextSecondary
                        )
                        Text(
                            text = "Terms of Service",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TrueBlueLight
                        )
                        Text(
                            text = " and ",
                            fontSize = 13.sp,
                            color = TextSecondary
                        )
                        Text(
                            text = "Privacy Policy",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TrueBlueLight
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Create Account Button
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .shadow(16.dp, RoundedCornerShape(28.dp), spotColor = TrueGreenLight)
                            .clip(RoundedCornerShape(28.dp))
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(TrueGreen, TrueGreenLight)
                                )
                            )
                            .clickable { onRegisterSuccess() },
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Create Account",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF061403)
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            AuthArrowRightIcon(tint = Color(0xFF061403))
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

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

                    Spacer(modifier = Modifier.height(18.dp))

                    // Field 6: Google Sign-Up Button
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .clip(RoundedCornerShape(26.dp))
                            .background(CinemaSurface)
                            .border(1.dp, CinemaBorder, RoundedCornerShape(26.dp))
                            .clickable { onRegisterSuccess() },
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
                                text = "Sign up with Google",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        }
                    }
                }

                // Bottom Footer Link
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, bottom = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Already have an account? ",
                            fontSize = 14.sp,
                            color = TextSecondary
                        )
                        Text(
                            text = "Sign In",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TrueBlueLight,
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .clickable { onNavigateToLogin() }
                                .padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF07090E)
@Composable
fun RegisterScreenPreview() {
    OTTPLATFORMTheme {
        RegisterScreen()
    }
}
