package com.unibuzz.campusapp.ui.screens

import android.widget.Toast
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.AccountBalanceWallet
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material.icons.rounded.LocalAtm
import androidx.compose.material.icons.rounded.NotificationsActive
import androidx.compose.material.icons.rounded.Savings
import androidx.compose.material.icons.rounded.Sell
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unibuzz.campusapp.ui.components.bounceOnClick
import com.unibuzz.campusapp.ui.theme.*

@Composable
fun SavingsScreen(
    onBack: (() -> Unit)? = null,
) {
    val context = LocalContext.current
    var isNotified by remember { mutableStateOf(false) }

    val infiniteTransition = rememberInfiniteTransition(label = "savingsPulse")
    val scalePulse by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "scalePulse"
    )

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
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                if (onBack != null) {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.width(48.dp))
                }

                Text(
                    text = "CAMPUS SAVINGS",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Black,
                        letterSpacing = 2.sp,
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                )

                Spacer(modifier = Modifier.width(48.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Main Hero Card - Coming Soon
            Surface(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(28.dp),
                border = BorderStroke(
                    1.5.dp,
                    Brush.horizontalGradient(
                        colors = listOf(
                            ElectricViolet,
                            NetflixRed,
                            EmeraldGreen,
                        )
                    )
                ),
                tonalElevation = 8.dp,
                shadowElevation = 12.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(scalePulse),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    // Pulsing Piggy Bank Badge
                    Surface(
                        shape = CircleShape,
                        color = ElectricViolet.copy(alpha = 0.2f),
                        border = BorderStroke(2.dp, ElectricViolet),
                        modifier = Modifier.size(90.dp),
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Savings,
                                contentDescription = "Savings Vault",
                                tint = EmeraldGreen,
                                modifier = Modifier.size(48.dp),
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Coming Soon Tag
                    Surface(
                        shape = CircleShape,
                        color = NetflixRed,
                        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.3f)),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.AutoAwesome,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp),
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "COMING SOON",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 1.5.sp,
                                ),
                                color = Color.White,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Smart Campus Savings & Budgeting",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "We are building an all-in-one financial hub tailored for university students. Save money on textbooks, earn cashback, track campus expenses, and unlock student discounts!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp,
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Notify Me Button
                    Button(
                        onClick = {
                            isNotified = !isNotified
                            val msg = if (isNotified) {
                                "Notifications enabled! You will be notified as soon as Campus Savings launches."
                            } else {
                                "Notification preference updated."
                            }
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isNotified) EmeraldGreen else ElectricViolet
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .bounceOnClick { },
                    ) {
                        Icon(
                            imageVector = if (isNotified) Icons.Rounded.CheckCircle else Icons.Rounded.NotificationsActive,
                            contentDescription = null,
                            tint = Color.White,
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isNotified) "NOTIFIED! WE'LL LET YOU KNOW" else "NOTIFY ME ON LAUNCH",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Upcoming Features Section Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Rounded.AutoAwesome,
                    contentDescription = null,
                    tint = ElectricViolet,
                    modifier = Modifier.size(20.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "WHAT'S COMING TO SAVINGS",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Feature Preview Cards
            SavingsFeatureCard(
                icon = Icons.Rounded.AccountBalanceWallet,
                iconColor = EmeraldGreen,
                title = "Student Goal Vaults",
                description = "Set micro-savings targets for textbooks, tech upgrades, or campus trips with automated goal tracking.",
            )

            Spacer(modifier = Modifier.height(12.dp))

            SavingsFeatureCard(
                icon = Icons.Rounded.Sell,
                iconColor = NetflixRed,
                title = "Verified Campus Deals",
                description = "Unlock exclusive student discounts on nearby food, coffee, gym memberships, and study spots.",
            )

            Spacer(modifier = Modifier.height(12.dp))

            SavingsFeatureCard(
                icon = Icons.Rounded.Groups,
                iconColor = ElectricViolet,
                title = "Group Buy Pools",
                description = "Pool funds with classmates or roommates to purchase supplies in bulk and split costs effortlessly.",
            )

            Spacer(modifier = Modifier.height(12.dp))

            SavingsFeatureCard(
                icon = Icons.Rounded.LocalAtm,
                iconColor = Color(0xFFFFB703),
                title = "Campus Marketplace Cashback",
                description = "Earn instant Unibuzz rewards and cashback when buying or selling items on the campus marketplace.",
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun SavingsFeatureCard(
    icon: ImageVector,
    iconColor: Color,
    title: String,
    description: String,
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, GlassBorderGradient),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.Top,
        ) {
            Surface(
                shape = CircleShape,
                color = iconColor.copy(alpha = 0.15f),
                border = BorderStroke(1.dp, iconColor.copy(alpha = 0.4f)),
                modifier = Modifier.size(48.dp),
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(24.dp),
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface,
                    )

                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                    ) {
                        Text(
                            text = "SOON",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 18.sp,
                )
            }
        }
    }
}
