package com.unibuzz.campusapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.EmojiEvents
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unibuzz.campusapp.ui.theme.*

@Composable
fun LeaderboardScreen(
    onBack: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
        ) {
            // Header Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onBackground,
                    )
                }

                Text(
                    text = "CAMPUS LEADERBOARD",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Black,
                        letterSpacing = 2.sp,
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                )

                Spacer(modifier = Modifier.width(48.dp))
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Top Hero Banner
            Surface(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(1.dp, GlassBorderGradient),
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFFFB703).copy(alpha = 0.2f),
                        border = BorderStroke(2.dp, Color(0xFFFFB703)),
                        modifier = Modifier.size(72.dp),
                    ) {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                            Icon(
                                imageVector = Icons.Rounded.EmojiEvents,
                                contentDescription = null,
                                tint = Color(0xFFFFB703),
                                modifier = Modifier.size(36.dp),
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Top Student Contributors",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Recognizing students with the most active marketplace listings and event RSVPs this semester.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Leaderboard Ranks
            LeaderboardRankRow(rank = 1, name = "Alex Rivera", handle = "@alex_unibuzz", score = "42 Listings • 18 Events", badgeColor = Color(0xFFFFB703))
            Spacer(modifier = Modifier.height(12.dp))
            LeaderboardRankRow(rank = 2, name = "Sarah Jenkins", handle = "@sarah_j", score = "35 Listings • 22 Events", badgeColor = Color(0xFF94A3B8))
            Spacer(modifier = Modifier.height(12.dp))
            LeaderboardRankRow(rank = 3, name = "Marcus Vance", handle = "@marcus_v", score = "29 Listings • 15 Events", badgeColor = Color(0xFFD97706))
            Spacer(modifier = Modifier.height(12.dp))
            LeaderboardRankRow(rank = 4, name = "Chloe Bennett", handle = "@chloe_b", score = "21 Listings • 19 Events", badgeColor = ElectricViolet)
            Spacer(modifier = Modifier.height(12.dp))
            LeaderboardRankRow(rank = 5, name = "Liam O'Connor", handle = "@liam_oc", score = "18 Listings • 12 Events", badgeColor = ElectricViolet)

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun LeaderboardRankRow(
    rank: Int,
    name: String,
    handle: String,
    score: String,
    badgeColor: Color,
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(1.dp, GlassBorderGradient),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = CircleShape,
                    color = badgeColor.copy(alpha = 0.2f),
                    border = BorderStroke(1.5.dp, badgeColor),
                    modifier = Modifier.size(38.dp),
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = "#$rank",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Black),
                            color = badgeColor,
                        )
                    }
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                        text = handle,
                        style = MaterialTheme.typography.bodySmall,
                        color = ElectricViolet,
                    )
                }
            }

            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.surfaceVariant,
            ) {
                Text(
                    text = score,
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                )
            }
        }
    }
}
