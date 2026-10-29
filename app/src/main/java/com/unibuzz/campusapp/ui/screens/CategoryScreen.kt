package com.unibuzz.campusapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.unibuzz.campusapp.ui.components.bounceOnClick
import com.unibuzz.campusapp.ui.theme.*

@Composable
fun CategoryScreen(
    onCategoryClick: (String) -> Unit = { },
) {
    var categoryFilter by remember { mutableStateOf("") }

    val categories = listOf(
        "" to "All Categories",
        "food" to "Food & Snacks",
        "books" to "Books & Notes",
        "electronics" to "Electronics",
        "phone" to "Phones & Accessories",
        "laptop" to "Laptops & Computing",
        "room_items" to "Dorm & Room Essentials",
        "fashion" to "Fashion & Apparel",
        "sports" to "Sports & Fitness",
        "other" to "Other Campus Goods",
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Browse Categories",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Black),
            color = MaterialTheme.colorScheme.onBackground,
        )
        Text(
            text = "Explore listings filtered by campus department & category",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 4.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            items(categories) { (key, label) ->
                val isSelected = categoryFilter == key
                Surface(
                    shape = CircleShape,
                    color = if (isSelected) NetflixRed else MaterialTheme.colorScheme.surfaceVariant,
                    border = BorderStroke(
                        1.dp,
                        if (isSelected) Color.Transparent else MaterialTheme.colorScheme.outline,
                    ),
                    modifier = Modifier
                        .clip(CircleShape)
                        .bounceOnClick {
                            categoryFilter = key
                            onCategoryClick(key)
                        },
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        ),
                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (categoryFilter.isNotEmpty()) {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, GlassBorderGradient),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(32.dp),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Category,
                        contentDescription = null,
                        modifier = Modifier.size(56.dp),
                        tint = NetflixRed,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Filtered by: ${categoryFilter.replaceFirstChar { it.uppercaseChar() }}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Matching student listings will appear in this feed.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        } else {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, GlassBorderGradient),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(32.dp),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Category,
                        contentDescription = "Categories",
                        modifier = Modifier.size(56.dp),
                        tint = NetflixRed,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Select a Category Above",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Tap any filter pill above to quickly locate student items.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }
}
