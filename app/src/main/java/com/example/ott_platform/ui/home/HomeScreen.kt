package com.example.ott_platform.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ott_platform.R
import com.example.ott_platform.ui.theme.BrandCyan
import com.example.ott_platform.ui.theme.BrandGold
import com.example.ott_platform.ui.theme.BrandPurple
import com.example.ott_platform.ui.theme.BrandRed
import com.example.ott_platform.ui.theme.BrandRedGlow
import com.example.ott_platform.ui.theme.TrueBlue
import com.example.ott_platform.ui.theme.TrueBlueLight
import com.example.ott_platform.ui.theme.TrueGreen
import com.example.ott_platform.ui.theme.TrueGreenLight
import com.example.ott_platform.ui.theme.CinemaBlack
import com.example.ott_platform.ui.theme.CinemaBorder
import com.example.ott_platform.ui.theme.CinemaDark
import com.example.ott_platform.ui.theme.CinemaSurface
import com.example.ott_platform.ui.theme.CinemaSurfaceVariant
import com.example.ott_platform.ui.theme.OTTPLATFORMTheme
import com.example.ott_platform.ui.theme.TextMuted
import com.example.ott_platform.ui.theme.TextPrimary
import com.example.ott_platform.ui.theme.TextSecondary

data class MovieItem(
    val title: String,
    val genre: String,
    val rating: String,
    val gradientColors: List<Color>,
    val progress: Float? = null
)

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onReplaySplash: () -> Unit = {}
) {
    val trendingList = listOf(
        MovieItem("Cyberpunk: 2099", "Sci-Fi • Thriller", "9.2", listOf(Color(0xFF8A2BE2), Color(0xFF0F172A))),
        MovieItem("Shadow Realm", "Action • Dark Fantasy", "8.8", listOf(Color(0xFFE50914), Color(0xFF1E1015))),
        MovieItem("Solaris Deep", "Adventure • Mystery", "8.5", listOf(Color(0xFF0284C7), Color(0xFF082F49))),
        MovieItem("Neon Dynasty", "Action • Drama", "8.9", listOf(Color(0xFFD97706), Color(0xFF451A03)))
    )

    val continueWatchingList = listOf(
        MovieItem("The Last Colony", "Season 2 • Ep 4", "8.7", listOf(Color(0xFF7C3AED), Color(0xFF1E1B4B)), 0.65f),
        MovieItem("Quantum Rift", "Season 1 • Ep 8", "9.1", listOf(Color(0xFFDC2626), Color(0xFF450A0A)), 0.35f),
        MovieItem("Midnight Signal", "1h 12m left", "8.4", listOf(Color(0xFF059669), Color(0xFF064E3B)), 0.82f)
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CinemaBlack)
            .verticalScroll(rememberScrollState())
    ) {
        // Top App Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // trueOTT Mini Logo Badge
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            Brush.linearGradient(
                                listOf(TrueBlue, TrueGreen)
                            )
                        )
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.trueott_emblem),
                        contentDescription = "trueOTT Mini Logo",
                        contentScale = ContentScale.Fit
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "true",
                        color = TrueBlueLight,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "OTT",
                        color = TrueGreenLight,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                }
            }

            // Replay Splash Action Button
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(CinemaSurfaceVariant)
                    .border(1.dp, CinemaBorder, RoundedCornerShape(16.dp))
                    .clickable { onReplaySplash() }
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "↺ Replay Splash",
                    color = BrandCyan,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // Hero Featured Movie Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(280.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF2E1065),
                            Color(0xFF881337),
                            CinemaDark
                        )
                    )
                )
                .border(1.dp, CinemaBorder, RoundedCornerShape(24.dp))
        ) {
            // Subtle Ambient Overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, CinemaBlack.copy(alpha = 0.85f))
                        )
                    )
            )

            // Content inside hero
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(20.dp)
            ) {
                // Category Pills
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    PillTag("FEATURED")
                    PillTag("4K ULTRA HD")
                    PillTag("IMDb 9.4", textColor = BrandGold)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "CHRONICLES OF AETHEL",
                    color = TextPrimary,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                )

                Text(
                    text = "Sci-Fi • Fantasy • Action • 2026",
                    color = TextSecondary,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(containerColor = BrandRed),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(text = "▶  Play", fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = {},
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CinemaBorder)
                    ) {
                        Text(text = "+ My List", color = TextPrimary, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Trending Now Section
        SectionHeader(title = "Trending Now")

        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(trendingList) { movie ->
                MovieCard(movie = movie)
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Continue Watching Section
        SectionHeader(title = "Continue Watching")

        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(continueWatchingList) { movie ->
                ContinueWatchingCard(movie = movie)
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
private fun SectionHeader(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = TextPrimary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "See All >",
            color = BrandRedGlow,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun MovieCard(movie: MovieItem) {
    Column(
        modifier = Modifier
            .width(140.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Box(
            modifier = Modifier
                .width(140.dp)
                .height(190.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Brush.verticalGradient(movie.gradientColors))
                .border(1.dp, CinemaBorder, RoundedCornerShape(16.dp))
                .padding(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .background(CinemaBlack.copy(alpha = 0.75f), shape = RoundedCornerShape(6.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = "★ ${movie.rating}",
                    color = BrandGold,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = movie.title,
            color = TextPrimary,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1
        )
        Text(
            text = movie.genre,
            color = TextMuted,
            fontSize = 11.sp,
            maxLines = 1
        )
    }
}

@Composable
private fun ContinueWatchingCard(movie: MovieItem) {
    Column(
        modifier = Modifier
            .width(180.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Box(
            modifier = Modifier
                .width(180.dp)
                .height(110.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Brush.linearGradient(movie.gradientColors))
                .border(1.dp, CinemaBorder, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            // Play circle
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(Color.Black.copy(alpha = 0.6f), shape = CircleShape)
                    .border(1.dp, Color.White.copy(alpha = 0.4f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "▶", color = Color.White, fontSize = 12.sp)
            }

            // Progress bar at bottom of card
            movie.progress?.let { progress ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .align(Alignment.BottomCenter)
                        .background(Color.Black.copy(alpha = 0.5f))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(fraction = progress)
                            .height(4.dp)
                            .background(BrandRed)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = movie.title,
            color = TextPrimary,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1
        )
        Text(
            text = movie.genre,
            color = TextMuted,
            fontSize = 11.sp,
            maxLines = 1
        )
    }
}

@Composable
private fun PillTag(text: String, textColor: Color = TextSecondary) {
    Box(
        modifier = Modifier
            .background(CinemaDark.copy(alpha = 0.8f), shape = RoundedCornerShape(6.dp))
            .border(0.8.dp, CinemaBorder, RoundedCornerShape(6.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(name = "Home Screen Preview", showBackground = true)
@Composable
fun HomeScreenPreview() {
    OTTPLATFORMTheme {
        HomeScreen()
    }
}
