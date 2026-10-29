package com.unibuzz.campusapp.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.LocalMall
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
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
import com.unibuzz.campusapp.data.model.Item
import com.unibuzz.campusapp.ui.components.bounceOnClick
import com.unibuzz.campusapp.ui.theme.*

@Composable
fun NetflixItemCard(
    item: Item,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    var isFavorite by remember { mutableStateOf(false) }
    val heartScale by animateFloatAsState(
        targetValue = if (isFavorite) 1.25f else 1.0f,
        animationSpec = spring(),
        label = "heartScale",
    )

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, GlassBorderGradient),
        shadowElevation = 8.dp,
        modifier = modifier
            .width(155.dp)
            .height(210.dp)
            .bounceOnClick(onClick = onClick),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            val imageUrl = item.imageUris.split(",").firstOrNull()?.trim()
            if (!imageUrl.isNullOrEmpty()) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = item.title,
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
                        imageVector = Icons.Default.LocalMall,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(36.dp),
                    )
                }
            }

            // Dark gradient overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(HeroOverlayGradient),
            )

            // Favorite Button Top-Left
            IconButton(
                onClick = {
                    isFavorite = !isFavorite
                    val msg = if (isFavorite) "Added to wishlist" else "Removed from wishlist"
                    android.widget.Toast.makeText(context, msg, android.widget.Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(4.dp)
                    .size(32.dp)
                    .scale(heartScale)
                    .background(Color.Black.copy(alpha = 0.5f), CircleShape),
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (isFavorite) NetflixRed else Color.White,
                    modifier = Modifier.size(18.dp),
                )
            }

            // Price badge at top-right
            Surface(
                color = EmeraldGreen,
                shape = RoundedCornerShape(bottomStart = 12.dp, topEnd = 16.dp),
                modifier = Modifier.align(Alignment.TopEnd),
            ) {
                Text(
                    text = item.priceText,
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                )
            }

            // Details at bottom
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(10.dp),
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = item.category,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                )
            }
        }
    }
}

@Composable
fun HeroSpotlightBanner(
    item: Item?,
    onItemClick: (Long) -> Unit,
    onPostClick: () -> Unit,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "badgePulse")
    val badgeScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "badgeScale",
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp),
    ) {
        val imageUrl = item?.imageUris?.split(",")?.firstOrNull()?.trim()
        if (!imageUrl.isNullOrEmpty()) {
            AsyncImage(
                model = imageUrl,
                contentDescription = item.title,
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
                    imageVector = Icons.Default.LocalOffer,
                    contentDescription = null,
                    tint = NetflixRed,
                    modifier = Modifier.size(64.dp),
                )
            }
        }

        // Dark gradient blend
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
                color = NetflixRed,
                shape = CircleShape,
                modifier = Modifier.scale(badgeScale),
            ) {
                Text(
                    text = "FEATURED SPOTLIGHT",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item?.title ?: "Campus Marketplace",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Black),
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (item != null) "${item.priceText} • ${item.category} • ${item.locationHint}" else "Buy, sell, and trade items effortlessly with verified students.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(14.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (item != null) {
                    Button(
                        onClick = { onItemClick(item.id) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onBackground,
                            contentColor = MaterialTheme.colorScheme.background,
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.bounceOnClick { onItemClick(item.id) },
                    ) {
                        Icon(Icons.Default.Info, contentDescription = null, tint = MaterialTheme.colorScheme.background)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("View Item", color = MaterialTheme.colorScheme.background, fontWeight = FontWeight.Bold)
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
                    Text("Post Listing", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemListScreen(
    onItemClick: (Long) -> Unit,
    onPostClick: () -> Unit = { },
    database: AppDatabase? = null,
    externalSearchQuery: String = "",
) {
    val context = LocalContext.current
    val db = database ?: remember(context) { AppDatabase.getInstance(context) }
    val itemList by db.itemDao()
        .getAllAsFlow()
        .collectAsState(initial = emptyList())

    val searchQuery = externalSearchQuery
    var selectedCategory by remember { mutableStateOf("All") }
    val categories = listOf("All", "Food & Snacks", "Books", "Electronics", "Fashion", "Sports", "Room Items", "Other")

    val filteredList = itemList.filter { item ->
        val matchesCategory = selectedCategory == "All" || item.category.equals(selectedCategory, ignoreCase = true)
        val matchesSearch = searchQuery.isBlank() ||
            item.title.contains(searchQuery, ignoreCase = true) ||
            item.description.contains(searchQuery, ignoreCase = true) ||
            item.category.contains(searchQuery, ignoreCase = true)
        matchesCategory && matchesSearch
    }

    var sortOption by remember { mutableStateOf("Default") }
    val sortOptions = listOf("Default", "Price: Low to High", "Price: High to Low", "Newest")

    val sortedList = remember(filteredList, sortOption) {
        fun parsePrice(priceText: String): Double {
            val clean = priceText.replace(Regex("[^0-9.]"), "")
            return clean.toDoubleOrNull() ?: 0.0
        }
        when (sortOption) {
            "Price: Low to High" -> filteredList.sortedBy { parsePrice(it.priceText) }
            "Price: High to Low" -> filteredList.sortedByDescending { parsePrice(it.priceText) }
            "Newest" -> filteredList.sortedByDescending { it.createdAt }
            else -> filteredList
        }
    }

    val featuredItem = itemList.firstOrNull()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 100.dp),
    ) {
        // Hero Spotlight Top Banner
        item {
            HeroSpotlightBanner(
                item = featuredItem,
                onItemClick = onItemClick,
                onPostClick = onPostClick,
            )
        }

        // Category Filter Chips Row
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                items(categories) { cat ->
                    val isSelected = selectedCategory == cat
                    Surface(
                        shape = CircleShape,
                        color = if (isSelected) NetflixRed else MaterialTheme.colorScheme.surfaceVariant,
                        border = BorderStroke(
                            1.dp,
                            if (isSelected) Color.Transparent else MaterialTheme.colorScheme.outline,
                        ),
                        modifier = Modifier
                            .clip(CircleShape)
                            .bounceOnClick { selectedCategory = cat },
                    ) {
                        Text(
                            text = cat,
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

        // Sort Option Chips Row
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                items(sortOptions) { opt ->
                    val isSelected = sortOption == opt
                    Surface(
                        shape = CircleShape,
                        color = if (isSelected) ElectricViolet else MaterialTheme.colorScheme.surfaceVariant,
                        border = BorderStroke(
                            1.dp,
                            if (isSelected) Color.Transparent else MaterialTheme.colorScheme.outline,
                        ),
                        modifier = Modifier
                            .clip(CircleShape)
                            .bounceOnClick { sortOption = opt },
                    ) {
                        Text(
                            text = opt,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            ),
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                        )
                    }
                }
            }
        }

        // Section 1: Trending Marketplace Items
        item {
            Column(modifier = Modifier.padding(vertical = 12.dp)) {
                Text(
                    text = if (searchQuery.isNotBlank()) "Search Results (${sortedList.size})" else "Trending Marketplace Deals",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                )

                if (itemList.isEmpty()) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                    ) {
                        items(3) {
                            ItemSkeletonCard()
                        }
                    }
                } else if (sortedList.isEmpty()) {
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
                                imageVector = Icons.Default.LocalMall,
                                contentDescription = null,
                                tint = NetflixRed,
                                modifier = Modifier.size(48.dp),
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = if (searchQuery.isNotBlank()) "No items match '$searchQuery'" else "No items listed yet",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Be the first student to post in this section!",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = onPostClick,
                                colors = ButtonDefaults.buttonColors(containerColor = NetflixRed),
                            ) {
                                Text("Post Listing", color = Color.White)
                            }
                        }
                    }
                } else {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                    ) {
                        items(sortedList) { item ->
                            NetflixItemCard(
                                item = item,
                                onClick = { onItemClick(item.id) },
                            )
                        }
                    }
                }
            }
        }

        // Section 2: Fresh Postings Grid / Row
        if (itemList.size > 1 && searchQuery.isBlank()) {
            item {
                Column(modifier = Modifier.padding(vertical = 12.dp)) {
                    Text(
                        text = "Recently Added On Campus",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                    )

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                    ) {
                        items(itemList.reversed()) { item ->
                            NetflixItemCard(
                                item = item,
                                onClick = { onItemClick(item.id) },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemSkeletonCard() {
    val infiniteTransition = rememberInfiniteTransition(label = "skeletonPulse")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "alphaPulse",
    )

    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = alpha),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, GlassBorderGradient),
        modifier = Modifier
            .width(180.dp)
            .height(260.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f), RoundedCornerShape(14.dp)),
            )

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(16.dp)
                        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f), RoundedCornerShape(4.dp)),
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(14.dp)
                        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f), RoundedCornerShape(4.dp)),
                )
            }
        }
    }
}
