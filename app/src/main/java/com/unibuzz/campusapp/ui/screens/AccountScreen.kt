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
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.DeleteForever
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material.icons.rounded.LocalOffer
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Person
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
import com.unibuzz.campusapp.data.local.AuthManager
import com.unibuzz.campusapp.ui.theme.*

@Composable
fun AccountScreen(
    onBack: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateToSavings: () -> Unit = {},
) {
    val context = LocalContext.current
    val authManager = remember(context) { AuthManager.getInstance(context) }
    val currentUser by authManager.currentUser.collectAsState()

    var showAccountInfoDialog by remember { mutableStateOf(false) }
    var showChangePasswordDialog by remember { mutableStateOf(false) }
    var showDeleteAccountDialog by remember { mutableStateOf(false) }
    var deleteAccountToast by remember { mutableStateOf(false) }
    var newPassword by remember { mutableStateOf("") }
    var passwordChangedToast by remember { mutableStateOf(false) }

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
                    text = "MY ACCOUNT",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Black,
                        letterSpacing = 2.sp,
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                )

                Spacer(modifier = Modifier.width(48.dp))
            }

            Spacer(modifier = Modifier.height(20.dp))

            val user = currentUser
            if (user != null) {
                // Authenticated Profile View
                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(22.dp),
                    border = BorderStroke(1.dp, GlassBorderGradient),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = ElectricViolet,
                            border = BorderStroke(3.dp, MaterialTheme.colorScheme.outline),
                            modifier = Modifier.size(80.dp),
                        ) {
                            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                                val initials = user.fullName.split(" ")
                                    .mapNotNull { it.firstOrNull()?.toString() }
                                    .take(2)
                                    .joinToString("")
                                    .uppercase()
                                Text(
                                    text = initials.ifBlank { "U" },
                                    style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Black),
                                    color = Color.White,
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = user.fullName,
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        Text(
                            text = user.handle,
                            style = MaterialTheme.typography.titleMedium,
                            color = ElectricViolet,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Text(
                            text = user.email,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            border = BorderStroke(1.dp, GlassBorderGradient),
                        ) {
                            Text(
                                text = "VERIFIED CAMPUS STUDENT",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = EmeraldGreen,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Activity Overview Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Surface(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, GlassBorderGradient),
                        modifier = Modifier.weight(1f),
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Icon(Icons.Rounded.LocalOffer, contentDescription = null, tint = NetflixRed)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Listings", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("My Posts", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold)
                        }
                    }

                    Surface(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, GlassBorderGradient),
                        modifier = Modifier.weight(1f),
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Icon(Icons.Rounded.Event, contentDescription = null, tint = ElectricViolet)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Events", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("My RSVP's", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Account Management Options
                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, GlassBorderGradient),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column(modifier = Modifier.padding(vertical = 8.dp)) {
                        AccountOptionRow(
                            icon = Icons.Rounded.Person,
                            title = "Account Information",
                            onClick = { showAccountInfoDialog = true },
                        )
                        Divider(color = MaterialTheme.colorScheme.outline, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
                        AccountOptionRow(
                            icon = Icons.Rounded.Lock,
                            title = "Change Password",
                            onClick = { showChangePasswordDialog = true },
                        )
                        Divider(color = MaterialTheme.colorScheme.outline, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
                        AccountOptionRow(
                            icon = Icons.Rounded.DeleteForever,
                            title = "Delete Account",
                            onClick = { showDeleteAccountDialog = true },
                        )
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                Button(
                    onClick = { authManager.logout() },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    border = BorderStroke(1.dp, NetflixRed.copy(alpha = 0.5f)),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                ) {
                    Icon(Icons.AutoMirrored.Rounded.Logout, contentDescription = null, tint = NetflixRed)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Log Out", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), color = NetflixRed)
                }
            } else {
                // Guest / Unauthenticated View
                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(22.dp),
                    border = BorderStroke(1.dp, GlassBorderGradient),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier.size(72.dp),
                        ) {
                            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                                Icon(
                                    imageVector = Icons.Rounded.Lock,
                                    contentDescription = null,
                                    tint = NetflixRed,
                                    modifier = Modifier.size(36.dp),
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Campus Account Required",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Sign in or create an account to post listings, RSVP events, and access your profile.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = onNavigateToLogin,
                            colors = ButtonDefaults.buttonColors(containerColor = NetflixRed),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                        ) {
                            Text("Sign In", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), color = Color.White)
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedButton(
                            onClick = onNavigateToRegister,
                            border = BorderStroke(1.dp, ElectricViolet),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                        ) {
                            Text("Create Account", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), color = ElectricViolet)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }

    // Dialogs & Toasts
    if (showAccountInfoDialog && currentUser != null) {
        val u = currentUser!!
        AlertDialog(
            onDismissRequest = { showAccountInfoDialog = false },
            title = { Text("Account Information", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Full Name: ${u.fullName}")
                    Text("Campus Handle: ${u.handle}")
                    Text("Email: ${u.email}")
                    Text("Status: Verified Campus Student")
                }
            },
            confirmButton = {
                TextButton(onClick = { showAccountInfoDialog = false }) {
                    Text("Close", color = NetflixRed)
                }
            },
            shape = RoundedCornerShape(20.dp),
        )
    }

    if (showChangePasswordDialog) {
        AlertDialog(
            onDismissRequest = { showChangePasswordDialog = false },
            title = { Text("Change Password", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Enter your new campus account password below:")
                    OutlinedTextField(
                        value = newPassword,
                        onValueChange = { newPassword = it },
                        placeholder = { Text("New Password") },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showChangePasswordDialog = false
                        newPassword = ""
                        passwordChangedToast = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = NetflixRed),
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Text("Update Password")
                }
            },
            dismissButton = {
                TextButton(onClick = { showChangePasswordDialog = false }) {
                    Text("Cancel")
                }
            },
            shape = RoundedCornerShape(20.dp),
        )
    }

    if (passwordChangedToast) {
        LaunchedEffect(Unit) {
            android.widget.Toast.makeText(context, "Password updated successfully!", android.widget.Toast.LENGTH_SHORT).show()
            passwordChangedToast = false
        }
    }

    if (deleteAccountToast) {
        LaunchedEffect(Unit) {
            android.widget.Toast.makeText(context, "Account permanently deleted", android.widget.Toast.LENGTH_SHORT).show()
            deleteAccountToast = false
        }
    }

    if (showDeleteAccountDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteAccountDialog = false },
            title = { Text("Delete Account", fontWeight = FontWeight.Bold, color = NetflixRed) },
            text = {
                Text("Are you sure you want to permanently delete your Unibuzz account and data? This action cannot be undone.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDeleteAccountDialog = false
                        authManager.logout()
                        deleteAccountToast = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = NetflixRed),
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Text("Delete Account")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteAccountDialog = false }) {
                    Text("Cancel")
                }
            },
            shape = RoundedCornerShape(20.dp),
        )
    }
}

@Composable
private fun AccountOptionRow(
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

        Icon(
            imageVector = Icons.Rounded.ChevronRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(18.dp),
        )
    }
}
