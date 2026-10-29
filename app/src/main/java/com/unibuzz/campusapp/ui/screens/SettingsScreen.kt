package com.unibuzz.campusapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.Help
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.Fingerprint
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Security
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unibuzz.campusapp.data.local.AppFontFamily
import com.unibuzz.campusapp.data.local.ThemeManager
import com.unibuzz.campusapp.data.local.ThemeMode
import com.unibuzz.campusapp.ui.theme.*

@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onNavigateToAbout: () -> Unit,
) {
    val context = LocalContext.current
    val themeManager = remember(context) { ThemeManager.getInstance(context) }
    val themeMode by themeManager.themeMode.collectAsState()
    val appFontFamily by themeManager.appFontFamily.collectAsState()

    var biometricEnabled by remember { mutableStateOf(false) }
    var biometricToastMessage by remember { mutableStateOf<String?>(null) }
    var showSupportDialog by remember { mutableStateOf(false) }
    var showNotificationsDialog by remember { mutableStateOf(false) }
    var showPrivacyDialog by remember { mutableStateOf(false) }

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
                    text = "APP SETTINGS",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Black,
                        letterSpacing = 2.sp,
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                )

                Spacer(modifier = Modifier.width(48.dp))
            }

            Spacer(modifier = Modifier.height(20.dp))

            // App Theme Selector
            Surface(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, GlassBorderGradient),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "App Theme",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        val modes = listOf(
                            ThemeMode.DARK to "Dark",
                            ThemeMode.LIGHT to "Light",
                            ThemeMode.SYSTEM to "System",
                        )
                        modes.forEach { (mode, label) ->
                            val selected = themeMode == mode
                            Surface(
                                shape = CircleShape,
                                color = if (selected) NetflixRed else MaterialTheme.colorScheme.surfaceVariant,
                                border = BorderStroke(
                                    1.dp,
                                    if (selected) Color.Transparent else MaterialTheme.colorScheme.outline,
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(CircleShape)
                                    .clickable { themeManager.setThemeMode(mode) },
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.padding(vertical = 10.dp),
                                ) {
                                    Text(
                                        text = label,
                                        style = MaterialTheme.typography.labelLarge.copy(
                                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                                        ),
                                        color = if (selected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Font Family Selector
            Surface(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, GlassBorderGradient),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "Font Family",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.surfaceVariant,
                        ) {
                            Text(
                                text = appFontFamily.label,
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = ElectricViolet,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        AppFontFamily.entries.forEach { fontOption ->
                            val selected = appFontFamily == fontOption
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
                                    .clickable { themeManager.setFontFamily(fontOption) },
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.padding(vertical = 10.dp),
                                ) {
                                    Text(
                                        text = fontOption.label.split(" ").first(),
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                                            fontFamily = getFontFamily(fontOption),
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

            Spacer(modifier = Modifier.height(14.dp))

            // Settings Options Menu
            Surface(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, GlassBorderGradient),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    SettingsOptionRow(
                        icon = Icons.Rounded.Notifications,
                        title = "Notification Settings",
                        onClick = { showNotificationsDialog = true },
                    )
                    Divider(color = MaterialTheme.colorScheme.outline, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
                    SettingsOptionRow(
                        icon = Icons.Rounded.Security,
                        title = "Privacy & Campus Tag",
                        onClick = { showPrivacyDialog = true },
                    )
                    Divider(color = MaterialTheme.colorScheme.outline, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
                    SettingsOptionRow(
                        icon = Icons.Rounded.Fingerprint,
                        title = "Biometric Authentication",
                        badge = if (biometricEnabled) "ENABLED" else "DISABLED",
                        onClick = {
                            biometricEnabled = !biometricEnabled
                            biometricToastMessage = if (biometricEnabled) "Biometric authentication enabled" else "Biometric authentication disabled"
                        },
                    )
                    Divider(color = MaterialTheme.colorScheme.outline, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
                    SettingsOptionRow(
                        icon = Icons.AutoMirrored.Rounded.Help,
                        title = "Support & Help Desk",
                        onClick = { showSupportDialog = true },
                    )
                    Divider(color = MaterialTheme.colorScheme.outline, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
                    SettingsOptionRow(
                        icon = Icons.Rounded.Info,
                        title = "About Unibuzz & Developer",
                        onClick = onNavigateToAbout,
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }

    // Dialogs & Toasts
    if (biometricToastMessage != null) {
        val msg = biometricToastMessage!!
        LaunchedEffect(msg) {
            android.widget.Toast.makeText(context, msg, android.widget.Toast.LENGTH_SHORT).show()
            biometricToastMessage = null
        }
    }

    if (showNotificationsDialog) {
        AlertDialog(
            onDismissRequest = { showNotificationsDialog = false },
            title = { Text("Notification Settings", fontWeight = FontWeight.Bold) },
            text = { Text("Push notifications for campus marketplace items, event RSVPs, and chat messages are enabled.") },
            confirmButton = {
                TextButton(onClick = { showNotificationsDialog = false }) {
                    Text("Close", color = NetflixRed)
                }
            },
            shape = RoundedCornerShape(20.dp),
        )
    }

    if (showPrivacyDialog) {
        AlertDialog(
            onDismissRequest = { showPrivacyDialog = false },
            title = { Text("Privacy & Campus Tag", fontWeight = FontWeight.Bold) },
            text = { Text("Your campus tag is visible only to verified students of your university. Data is stored securely and locally.") },
            confirmButton = {
                TextButton(onClick = { showPrivacyDialog = false }) {
                    Text("Close", color = NetflixRed)
                }
            },
            shape = RoundedCornerShape(20.dp),
        )
    }

    if (showSupportDialog) {
        AlertDialog(
            onDismissRequest = { showSupportDialog = false },
            title = { Text("Campus Support & Help Desk", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Need help with app settings or campus listings?")
                    Text("Email: support@unibuzz.campusapp")
                    Text("Help Desk: Student Union Building, Room 204")
                }
            },
            confirmButton = {
                TextButton(onClick = { showSupportDialog = false }) {
                    Text("Got it", color = NetflixRed)
                }
            },
            shape = RoundedCornerShape(20.dp),
        )
    }
}

@Composable
private fun SettingsOptionRow(
    icon: ImageVector,
    title: String,
    badge: String? = null,
    onClick: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(14.dp))
            Text(text = title, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurface)
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            if (badge != null) {
                Surface(
                    shape = CircleShape,
                    color = NetflixRed.copy(alpha = 0.2f),
                    border = BorderStroke(1.dp, NetflixRed),
                ) {
                    Text(
                        text = badge,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = NetflixRed,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
            Icon(
                imageVector = Icons.Rounded.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(18.dp),
            )
        }
    }
}
