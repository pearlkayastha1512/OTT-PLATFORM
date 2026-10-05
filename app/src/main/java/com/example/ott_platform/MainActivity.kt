package com.example.ott_platform

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.ott_platform.tenant.TenantRepository
import com.example.ott_platform.ui.home.HomeScreen
import com.example.ott_platform.ui.onboarding.OnboardingScreen
import com.example.ott_platform.ui.splash.SplashScreen
import com.example.ott_platform.ui.theme.OTTPLATFORMTheme

enum class AppScreen {
    Splash,
    Onboarding,
    Home
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Install official AndroidX SplashScreen for seamless cold-boot
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            OTTPLATFORMTheme {
                OTTApp()
            }
        }
    }
}

@Composable
fun OTTApp() {
    var currentScreen by remember { mutableStateOf(AppScreen.Splash) }

    Crossfade(
        targetState = currentScreen,
        animationSpec = tween(durationMillis = 600),
        label = "screenCrossfade"
    ) { screen ->
        when (screen) {
            AppScreen.Splash -> {
                SplashScreen(
                    modifier = Modifier.fillMaxSize(),
                    onSplashFinished = {
                        currentScreen = AppScreen.Onboarding
                    }
                )
            }
            AppScreen.Onboarding -> {
                OnboardingScreen(
                    modifier = Modifier.fillMaxSize(),
                    tenantConfig = TenantRepository.currentTenant.value,
                    onFinishOnboarding = {
                        currentScreen = AppScreen.Home
                    }
                )
            }
            AppScreen.Home -> {
                HomeScreen(
                    modifier = Modifier.fillMaxSize(),
                    onReplaySplash = {
                        currentScreen = AppScreen.Onboarding
                    }
                )
            }
        }
    }
}