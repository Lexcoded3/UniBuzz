package com.unibuzz.campusapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.material.icons.rounded.ExpandLess
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material.icons.rounded.Quiz
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unibuzz.campusapp.ui.theme.*

@Composable
fun FaqsScreen(
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
                    text = "CAMPUS FAQS",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Black,
                        letterSpacing = 2.sp,
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                )

                Spacer(modifier = Modifier.width(48.dp))
            }

            Spacer(modifier = Modifier.height(20.dp))

            FaqItem(
                question = "How do I post an item on the Unibuzz marketplace?",
                answer = "Tap the 'Post Listing' button on the Items tab or bottom navigation. Upload up to 4 photos, enter a title, description, price (or mark as FREE), condition, category, and your campus meeting location.",
            )
            Spacer(modifier = Modifier.height(12.dp))

            FaqItem(
                question = "Are transactions handled in the app?",
                answer = "Unibuzz is a local-first peer-to-peer campus platform. Buyers and sellers connect directly via contact info provided in the listing and complete exchanges safely on campus (e.g. Student Union or Library).",
            )
            Spacer(modifier = Modifier.height(12.dp))

            FaqItem(
                question = "How do I create or RSVP to campus events?",
                answer = "Head over to the Events tab and tap 'Post Event' to publish society meetups, sports tournaments, or parties. You can also bookmark upcoming events to save them to your radar.",
            )
            Spacer(modifier = Modifier.height(12.dp))

            FaqItem(
                question = "Is Unibuzz free for students?",
                answer = "Yes! Unibuzz is completely free for university students to buy, sell, trade, and discover campus happenings.",
            )
            Spacer(modifier = Modifier.height(12.dp))

            FaqItem(
                question = "How do I change the app theme or font family?",
                answer = "Open your Account page (profile icon in the top right), where you can instantly switch between Dark, Light, or System themes and customize your font family (Modern Sans, Serif, Tech Mono, or Playful Cursive).",
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun FaqItem(
    question: String,
    answer: String,
) {
    var expanded by remember { mutableStateOf(false) }

    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, GlassBorderGradient),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable { expanded = !expanded },
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f),
                ) {
                    Surface(
                        shape = CircleShape,
                        color = ElectricViolet.copy(alpha = 0.15f),
                        modifier = Modifier.size(36.dp),
                    ) {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                            Icon(
                                imageVector = Icons.Rounded.Quiz,
                                contentDescription = null,
                                tint = ElectricViolet,
                                modifier = Modifier.size(18.dp),
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = question,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }

                Icon(
                    imageVector = if (expanded) Icons.Rounded.ExpandLess else Icons.Rounded.ExpandMore,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                Column {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = answer,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 20.sp,
                    )
                }
            }
        }
    }
}
