package com.unibuzz.campusapp.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.AddPhotoAlternate
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.ContactPhone
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.LocalOffer
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Sell
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.unibuzz.campusapp.data.local.AppDatabase
import com.unibuzz.campusapp.data.model.Item
import com.unibuzz.campusapp.ui.components.bounceOnClick
import com.unibuzz.campusapp.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostItemScreen(
    onBack: () -> Unit,
    onPosted: () -> Unit = {},
    database: AppDatabase? = null,
) {
    val context = LocalContext.current
    val db = database ?: remember(context) { AppDatabase.getInstance(context) }
    val coroutineScope = rememberCoroutineScope()

    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var priceText by rememberSaveable { mutableStateOf("") }
    var isFree by rememberSaveable { mutableStateOf(false) }
    var condition by rememberSaveable { mutableStateOf("Like New") }
    var category by rememberSaveable { mutableStateOf("Books") }
    var locationHint by rememberSaveable { mutableStateOf("") }
    var contact by rememberSaveable { mutableStateOf("") }
    var posterHandle by rememberSaveable { mutableStateOf("") }
    var selectedImages by remember { mutableStateOf<List<Uri>>(emptyList()) }
    var isSubmitting by remember { mutableStateOf(false) }

    val categories = listOf("Food & Snacks", "Books", "Tech", "Furniture", "Clothing", "Sports", "Other")
    val conditions = listOf("Brand New", "Like New", "Good", "Fair")

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
    ) { uris ->
        if (uris.isNotEmpty()) {
            val combined = (selectedImages + uris).distinct().take(4)
            selectedImages = combined
        }
    }

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
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .size(40.dp)
                        .background(MaterialTheme.colorScheme.surface, CircleShape)
                        .border(1.dp, MaterialTheme.colorScheme.outline, CircleShape),
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }

                Text(
                    text = "POST A LISTING",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Black,
                        letterSpacing = 2.sp,
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                )

                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    border = BorderStroke(1.dp, GlassBorderGradient),
                ) {
                    Text(
                        text = "MARKET",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = NetflixRed,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Photo Upload Hero Box
            Surface(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(1.dp, GlassBorderGradient),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "ITEM PHOTOS",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp,
                            ),
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        Text(
                            text = "${selectedImages.size}/4 MAX",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = if (selectedImages.size == 4) NetflixRed else MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    if (selectedImages.isEmpty()) {
                        // Empty State Upload Target
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(130.dp)
                                .clip(RoundedCornerShape(18.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .border(
                                    1.5.dp,
                                    Brush.linearGradient(
                                        colors = listOf(
                                            NetflixRed.copy(alpha = 0.5f),
                                            ElectricViolet.copy(alpha = 0.5f)
                                        )
                                    ),
                                    RoundedCornerShape(18.dp)
                                )
                                .bounceOnClick { imagePicker.launch("image/*") },
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Surface(
                                    shape = CircleShape,
                                    color = NetflixRed.copy(alpha = 0.2f),
                                    modifier = Modifier.size(52.dp),
                                ) {
                                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                                        Icon(
                                            imageVector = Icons.Rounded.AddPhotoAlternate,
                                            contentDescription = null,
                                            tint = NetflixRed,
                                            modifier = Modifier.size(28.dp),
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Tap to upload item photos",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onSurface,
                                )
                                Text(
                                    text = "PNG, JPG up to 4 images",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                        }
                    } else {
                        // Image Preview Row
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            itemsIndexed(selectedImages) { index, uri ->
                                Box(modifier = Modifier.size(96.dp)) {
                                    AsyncImage(
                                        model = uri,
                                        contentDescription = "Photo $index",
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(RoundedCornerShape(16.dp))
                                            .border(1.dp, GlassBorderGradient, RoundedCornerShape(16.dp)),
                                        contentScale = ContentScale.Crop,
                                    )
                                    // Delete Photo Button
                                    IconButton(
                                        onClick = {
                                            selectedImages = selectedImages.toMutableList().apply { removeAt(index) }
                                        },
                                        modifier = Modifier
                                            .size(26.dp)
                                            .align(Alignment.TopEnd)
                                            .padding(2.dp)
                                            .background(Color.Black.copy(alpha = 0.7f), CircleShape),
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.Close,
                                            contentDescription = "Remove photo",
                                            tint = Color.White,
                                            modifier = Modifier.size(14.dp),
                                        )
                                    }
                                }
                            }

                            if (selectedImages.size < 4) {
                                item {
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier
                                            .size(96.dp)
                                            .clip(RoundedCornerShape(16.dp))
                                            .background(MaterialTheme.colorScheme.surfaceVariant)
                                            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(16.dp))
                                            .bounceOnClick { imagePicker.launch("image/*") },
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.AddPhotoAlternate,
                                            contentDescription = "Add more",
                                            tint = ElectricViolet,
                                            modifier = Modifier.size(32.dp),
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Title & Description Inputs
            Surface(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(1.dp, GlassBorderGradient),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "ITEM DETAILS",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp,
                        ),
                        color = MaterialTheme.colorScheme.onSurface,
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    CoolTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = "Listing Title",
                        placeholder = "e.g. Calculus Textbook 10th Ed.",
                        icon = Icons.Rounded.LocalOffer,
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    CoolTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = "Description",
                        placeholder = "Describe condition, edition, specs, reason for selling...",
                        icon = Icons.Rounded.Description,
                        singleLine = false,
                        minLines = 3,
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Pricing & Free Toggle Card
            Surface(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(1.dp, GlassBorderGradient),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column {
                            Text(
                                text = "PRICE & GIVEAWAY",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp,
                                ),
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                            Text(
                                text = if (isFree) "Giving away for free" else "Set selling price",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "FREE",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                color = if (isFree) EmeraldGreen else MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Switch(
                                checked = isFree,
                                onCheckedChange = { isFree = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = EmeraldGreen,
                                    uncheckedThumbColor = Color.Gray,
                                    uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant,
                                ),
                            )
                        }
                    }

                    AnimatedVisibility(visible = !isFree, enter = fadeIn(), exit = fadeOut()) {
                        Column {
                            Spacer(modifier = Modifier.height(14.dp))
                            CoolTextField(
                                value = priceText,
                                onValueChange = { priceText = it },
                                label = "Price ($)",
                                placeholder = "e.g. 25.00",
                                icon = Icons.Rounded.Sell,
                                keyboardType = KeyboardType.Number,
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Category & Condition Selectors
            Surface(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(1.dp, GlassBorderGradient),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "CATEGORY",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp,
                        ),
                        color = MaterialTheme.colorScheme.onSurface,
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        items(categories) { cat ->
                            val selected = category.equals(cat, ignoreCase = true)
                            FilterChip(
                                selected = selected,
                                onClick = { category = cat },
                                label = { Text(cat, fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium) },
                                leadingIcon = if (selected) {
                                    { Icon(Icons.Rounded.Check, contentDescription = null, modifier = Modifier.size(16.dp)) }
                                } else null,
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = NetflixRed,
                                    selectedLabelColor = Color.White,
                                    selectedLeadingIconColor = Color.White,
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                    labelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                ),
                                border = FilterChipDefaults.filterChipBorder(
                                    borderColor = MaterialTheme.colorScheme.outline,
                                    selectedBorderColor = Color.Transparent,
                                ),
                                shape = CircleShape,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "CONDITION",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp,
                        ),
                        color = MaterialTheme.colorScheme.onSurface,
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        conditions.forEach { cond ->
                            val selected = condition.equals(cond, ignoreCase = true)
                            Surface(
                                shape = CircleShape,
                                color = if (selected) ElectricViolet else MaterialTheme.colorScheme.surfaceVariant,
                                border = BorderStroke(
                                    1.dp,
                                    if (selected) Color.Transparent else MaterialTheme.colorScheme.outline,
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(CircleShape)
                                    .clickable { condition = cond },
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.padding(vertical = 10.dp),
                                ) {
                                    Text(
                                        text = cond,
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                                            fontSize = 11.sp,
                                        ),
                                        color = if (selected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Location & Contact Details
            Surface(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(1.dp, GlassBorderGradient),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "PICKUP & CONTACT INFO",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp,
                        ),
                        color = MaterialTheme.colorScheme.onSurface,
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    CoolTextField(
                        value = locationHint,
                        onValueChange = { locationHint = it },
                        label = "Campus Location Hint",
                        placeholder = "e.g. Hall 3, Student Union, or Library",
                        icon = Icons.Rounded.LocationOn,
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    CoolTextField(
                        value = contact,
                        onValueChange = { contact = it },
                        label = "Contact Info",
                        placeholder = "Phone, WhatsApp, Telegram, or Email",
                        icon = Icons.Rounded.ContactPhone,
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    CoolTextField(
                        value = posterHandle,
                        onValueChange = { posterHandle = it },
                        label = "Your Handle",
                        placeholder = "@handle or anonymous",
                        icon = Icons.Rounded.Person,
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Publish Button
            Button(
                onClick = {
                    if (title.isBlank()) {
                        Toast.makeText(context, "Please enter a listing title", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    isSubmitting = true
                    val price = if (isFree) "Free" else if (priceText.isBlank()) "Free" else "$$priceText"
                    val imageUris = selectedImages.joinToString(",") { it.toString() }
                    val item = Item(
                        title = title.trim(),
                        description = description.ifBlank { "No description provided." },
                        priceText = price,
                        condition = condition,
                        category = category,
                        locationHint = locationHint.ifBlank { "On Campus" },
                        contact = contact.ifBlank { "Contact via Campus Unibuzz" },
                        imageUris = imageUris,
                        status = "available",
                        posterHandle = posterHandle.ifBlank { "student" },
                    )

                    coroutineScope.launch {
                        db.itemDao().insert(item)
                        Toast.makeText(context, "Item posted successfully!", Toast.LENGTH_SHORT).show()
                        onPosted()
                        onBack()
                    }
                },
                enabled = !isSubmitting,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(),
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(NetflixRed, ElectricViolet)
                        ),
                        shape = RoundedCornerShape(18.dp),
                    )
                    .bounceOnClick { },
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(22.dp),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isSubmitting) "PUBLISHING..." else "PUBLISH LISTING",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp,
                        ),
                        color = Color.White,
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun CoolTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    singleLine: Boolean = true,
    minLines: Int = 1,
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, style = MaterialTheme.typography.bodySmall) },
        placeholder = { Text(placeholder, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant) },
        leadingIcon = {
            Icon(imageVector = icon, contentDescription = null, tint = NetflixRed, modifier = Modifier.size(20.dp))
        },
        singleLine = singleLine,
        minLines = minLines,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedBorderColor = ElectricViolet,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            focusedLabelColor = ElectricViolet,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
    )
}
