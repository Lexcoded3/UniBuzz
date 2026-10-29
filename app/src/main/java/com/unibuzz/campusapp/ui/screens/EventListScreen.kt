package com.unibuzz.campusapp.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.unibuzz.campusapp.data.local.AppDatabase
import com.unibuzz.campusapp.data.model.Event
import com.unibuzz.campusapp.ui.components.bounceOnClick
import com.unibuzz.campusapp.ui.theme.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun NetflixEventCard(
    event: Event,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isBookmarked by remember { mutableStateOf(false) }
    val bookmarkScale by animateFloatAsState(
        targetValue = if (isBookmarked) 1.25f else 1.0f,
        animationSpec = spring(),
        label = "bookmarkScale",
    )

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, GlassBorderGradient),
        shadowElevation = 8.dp,
        modifier = modifier
            .width(170.dp)
            .height(220.dp)
            .bounceOnClick(onClick = onClick),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            val imageUrl = event.imageUris.split(",").firstOrNull()?.trim()
            if (!imageUrl.isNullOrEmpty()) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = event.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Default.Event,
                        contentDescription = null,
                        tint = ElectricViolet,
                        modifier = Modifier.size(40.dp),
                    )
                }
            }

            // Gradient Overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(HeroOverlayGradient),
            )

            // Bookmark Button Top-Left
            IconButton(
                onClick = { isBookmarked = !isBookmarked },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(4.dp)
                    .size(32.dp)
                    .scale(bookmarkScale)
                    .background(Color.Black.copy(alpha = 0.5f), CircleShape),
            ) {
                Icon(
                    imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                    contentDescription = "Bookmark",
                    tint = if (isBookmarked) ElectricViolet else Color.White,
                    modifier = Modifier.size(18.dp),
                )
            }

            // Date pill badge
            Surface(
                color = ElectricViolet,
                shape = RoundedCornerShape(bottomStart = 12.dp, topEnd = 16.dp),
                modifier = Modifier.align(Alignment.TopEnd),
            ) {
                Text(
                    text = formatShortDate(event.dateTime),
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                )
            }

            // Content at bottom
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(10.dp),
            ) {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(12.dp),
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = event.venue,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}

private fun formatShortDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd MMM", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

private fun formatFullDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("EEE, dd MMM • HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

@Composable
fun EventHeroSpotlightBanner(
    event: Event?,
    onEventClick: (Long) -> Unit,
    onPostClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp),
    ) {
        val imageUrl = event?.imageUris?.split(",")?.firstOrNull()?.trim()
        if (!imageUrl.isNullOrEmpty()) {
            AsyncImage(
                model = imageUrl,
                contentDescription = event.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.Event,
                    contentDescription = null,
                    tint = ElectricViolet,
                    modifier = Modifier.size(64.dp),
                )
            }
        }

        // Gradient blend
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(HeroOverlayGradient),
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp),
        ) {
            Surface(
                color = ElectricViolet,
                shape = CircleShape,
            ) {
                Text(
                    text = "FEATURED CAMPUS EVENT",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = event?.title ?: "Campus Event Spotlight",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Black),
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Schedule, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = if (event != null) "${formatFullDate(event.dateTime)} • ${event.venue}" else "Discover concerts, society meetups, tournaments, and parties.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Spacer(modifier = Modifier.height(14.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (event != null) {
                    Button(
                        onClick = { onEventClick(event.id) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onBackground,
                            contentColor = MaterialTheme.colorScheme.background,
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.bounceOnClick { onEventClick(event.id) },
                    ) {
                        Icon(Icons.Default.Info, contentDescription = null, tint = MaterialTheme.colorScheme.background)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("View Event", color = MaterialTheme.colorScheme.background, fontWeight = FontWeight.Bold)
                    }
                }

                Button(
                    onClick = onPostClick,
                    colors = ButtonDefaults.buttonColors(containerColor = NetflixRed),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.bounceOnClick(onClick = onPostClick),
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Post Event", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventListScreen(
    onEventClick: (Long) -> Unit,
    onPostClick: () -> Unit = { },
    database: AppDatabase? = null,
    externalSearchQuery: String = "",
) {
    val context = LocalContext.current
    val db = database ?: remember(context) { AppDatabase.getInstance(context) }
    val eventList by db.eventDao()
        .getAllAsFlow()
        .collectAsState(initial = emptyList())

    val searchQuery = externalSearchQuery
    var selectedType by remember { mutableStateOf("All") }
    val types = listOf("All", "Society", "Guild", "Party", "Concert", "Sports", "Other")

    val filteredList = eventList.filter { event ->
        val matchesType = selectedType == "All" || event.type.equals(selectedType, ignoreCase = true)
        val matchesSearch = searchQuery.isBlank() ||
            event.title.contains(searchQuery, ignoreCase = true) ||
            event.venue.contains(searchQuery, ignoreCase = true) ||
            event.organizer.contains(searchQuery, ignoreCase = true) ||
            event.type.contains(searchQuery, ignoreCase = true)
        matchesType && matchesSearch
    }

    val featuredEvent = eventList.firstOrNull()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 100.dp),
    ) {
        // Hero Event Spotlight Top Banner
        item {
            EventHeroSpotlightBanner(
                event = featuredEvent,
                onEventClick = onEventClick,
                onPostClick = onPostClick,
            )
        }

        // Type Filter Chips Row
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                items(types) { t ->
                    val isSelected = selectedType == t
                    Surface(
                        shape = CircleShape,
                        color = if (isSelected) ElectricViolet else MaterialTheme.colorScheme.surfaceVariant,
                        border = BorderStroke(
                            1.dp,
                            if (isSelected) Color.Transparent else MaterialTheme.colorScheme.outline,
                        ),
                        modifier = Modifier
                            .clip(CircleShape)
                            .bounceOnClick { selectedType = t },
                    ) {
                        Text(
                            text = t,
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            ),
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        )
                    }
                }
            }
        }

        // Section: Upcoming Campus Events
        item {
            Column(modifier = Modifier.padding(vertical = 12.dp)) {
                Text(
                    text = if (searchQuery.isNotBlank()) "Search Results (${filteredList.size})" else "Upcoming Campus Events",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                )

                if (filteredList.isEmpty()) {
                    Surface(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, GlassBorderGradient),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(32.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Default.Event,
                                contentDescription = null,
                                tint = ElectricViolet,
                                modifier = Modifier.size(48.dp),
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = if (searchQuery.isNotBlank()) "No events match '$searchQuery'" else "No events scheduled yet",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Be the first to create and publish a campus event!",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = onPostClick,
                                colors = ButtonDefaults.buttonColors(containerColor = NetflixRed),
                            ) {
                                Text("Post Event", color = Color.White)
                            }
                        }
                    }
                } else {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                    ) {
                        items(filteredList) { event ->
                            NetflixEventCard(
                                event = event,
                                onClick = { onEventClick(event.id) },
                            )
                        }
                    }
                }
            }
        }

        // Section 2: Recently Posted Events
        if (eventList.size > 1 && searchQuery.isBlank()) {
            item {
                Column(modifier = Modifier.padding(vertical = 12.dp)) {
                    Text(
                        text = "Recently Added Events",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                    )

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                    ) {
                        items(eventList.reversed()) { event ->
                            NetflixEventCard(
                                event = event,
                                onClick = { onEventClick(event.id) },
                            )
                        }
                    }
                }
            }
        }
    }
}
