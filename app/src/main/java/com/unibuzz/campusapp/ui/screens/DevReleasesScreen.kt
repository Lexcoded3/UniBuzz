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
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.NewReleases
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unibuzz.campusapp.ui.theme.*

@Composable
fun DevReleasesScreen(
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
                    text = "DEV RELEASES",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Black,
                        letterSpacing = 2.sp,
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                )

                Spacer(modifier = Modifier.width(48.dp))
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Latest Release Card
            ReleaseItemCard(
                version = "v1.0.0 Stable",
                date = "Latest • October 2025",
                tagColor = EmeraldGreen,
                highlights = listOf(
                    "Added navigation drawer menu with home, releases, leaderboard, settings, faqs & about.",
                    "Top app bar expandable search bar for real-time item & event filtering.",
                    "Font family customization (Modern Sans, Serif, Tech Mono, Playful Cursive).",
                    "Light Mode & Dark Mode dynamic theme support with system appearance sync.",
                    "Enhanced tab bar with spring bounce animations and custom IconKitchen assets.",
                ),
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Previous Release Card
            ReleaseItemCard(
                version = "v0.9.2 Beta",
                date = "September 2025",
                tagColor = ElectricViolet,
                highlights = listOf(
                    "Integrated Room database local-first repository with pre-populated campus seed data.",
                    "Multi-image picker for marketplace listings with preview and delete support.",
                    "Event RSVP and society meetups calendar feed.",
                    "Netflix-inspired Obsidian & Studio Clean Light color schemes.",
                ),
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Previous Release Card
            ReleaseItemCard(
                version = "v0.8.0 Alpha",
                date = "August 2025",
                tagColor = NetflixRed,
                highlights = listOf(
                    "Initial project scaffolding with Kotlin and Jetpack Compose M3.",
                    "Navigation Compose graph structure and student auth management.",
                ),
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun ReleaseItemCard(
    version: String,
    date: String,
    tagColor: androidx.compose.ui.graphics.Color,
    highlights: List<String>,
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(1.dp, GlassBorderGradient),
        shadowElevation = 6.dp,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = tagColor.copy(alpha = 0.15f),
                        modifier = Modifier.size(40.dp),
                    ) {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                            Icon(
                                imageVector = Icons.Rounded.NewReleases,
                                contentDescription = null,
                                tint = tagColor,
                                modifier = Modifier.size(20.dp),
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = version,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }

                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.surfaceVariant,
                ) {
                    Text(
                        text = date,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            highlights.forEach { highlight ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.Top,
                ) {
                    Icon(
                        imageVector = Icons.Rounded.CheckCircle,
                        contentDescription = null,
                        tint = tagColor,
                        modifier = Modifier
                            .size(16.dp)
                            .padding(top = 2.dp),
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = highlight,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 18.sp,
                    )
                }
            }
        }
    }
}
