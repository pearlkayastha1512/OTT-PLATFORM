package com.example.ott_platform.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val CinemaColorScheme = darkColorScheme(
    primary = TrueBlue,
    onPrimary = Color.White,
    primaryContainer = TrueBlueGlow,
    onPrimaryContainer = Color.White,
    secondary = TrueGreen,
    onSecondary = Color.White,
    tertiary = TrueBlueLight,
    background = CinemaBlack,
    onBackground = TextPrimary,
    surface = CinemaDark,
    onSurface = TextPrimary,
    surfaceVariant = CinemaSurface,
    onSurfaceVariant = TextSecondary,
    outline = CinemaBorder
)

private val LightColorScheme = lightColorScheme(
    primary = TrueBlue,
    onPrimary = Color.White,
    secondary = TrueGreen,
    background = Color(0xFFF6F7F9),
    surface = Color.White,
    onBackground = CinemaBlack,
    onSurface = CinemaBlack
)

@Composable
fun OTTPLATFORMTheme(
    darkTheme: Boolean = true, // OTT apps shine brightest in cinema dark mode
    dynamicColor: Boolean = false, // Keep brand aesthetic consistent
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> CinemaColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = CinemaBlack.toArgb()
                window.navigationBarColor = CinemaBlack.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
                WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}