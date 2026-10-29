package com.unibuzz.campusapp.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.unibuzz.campusapp.data.local.AppDatabase
import com.unibuzz.campusapp.data.model.Event
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostEventScreen(
    onBack: () -> Unit,
    onPosted: () -> Unit = { },
    database: AppDatabase? = null,
) {
    val context = LocalContext.current
    val db = database ?: remember(context) { AppDatabase.getInstance(context) }
    val coroutineScope = rememberCoroutineScope()

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var dateTimeMillis by rememberSaveable { mutableStateOf<Long?>(null) }
    var venue by remember { mutableStateOf("") }
    var organizer by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }
    var posterHandle by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("Society") }
    var selectedImage by rememberSaveable { mutableStateOf<Uri?>(null) }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ) { uri ->
        selectedImage = uri
    }

    LaunchedEffect(Unit) {
        if (posterHandle.isEmpty()) {
            posterHandle = "anonymous"
        }
    }

    Scaffold(
        topBar = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
        ) {
            Text(
                text = "Post an event",
                style = MaterialTheme.typography.headlineSmall,
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Event title") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 6,
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = type,
                onValueChange = { type = it },
                label = { Text("Event type") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                readOnly = true,
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = venue,
                onValueChange = { venue = it },
                label = { Text("Venue") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("e.g. Main hall, Block C, campus ground") },
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = organizer,
                onValueChange = { organizer = it },
                label = { Text("Organizer") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("e.g. Sports club, Guild, Society name") },
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = contact,
                onValueChange = { contact = it },
                label = { Text("Contact") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("Phone, Telegram, WhatsApp, or username") },
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = posterHandle,
                onValueChange = { posterHandle = it },
                label = { Text("Your handle") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("Optional campus handle") },
            )
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Poster image",
                style = MaterialTheme.typography.titleSmall,
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = if (selectedImage != null) "Image selected" else "No image yet",
                    style = MaterialTheme.typography.bodySmall,
                )
                OutlinedButton(onClick = { imagePicker.launch("image/*") }) {
                    Icon(
                        imageVector = Icons.Default.AddPhotoAlternate,
                        contentDescription = "Add image",
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Add")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            if (selectedImage != null) {
                AsyncImage(
                    model = selectedImage,
                    contentDescription = "Selected poster image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    contentScale = ContentScale.Crop,
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedButton(
                onClick = {
                    val imageUris = selectedImage?.toString() ?: ""
                    val event = Event(
                        title = title.ifBlank { "Untitled event" },
                        description = description.ifBlank { "No description" },
                        dateTime = dateTimeMillis ?: (System.currentTimeMillis() + 86400000L),
                        venue = venue.ifBlank { "Campus" },
                        organizer = organizer.ifBlank { "Unknown" },
                        contact = contact.ifBlank { "Contact the organizer" },
                        imageUris = imageUris,
                        type = type,
                        status = "upcoming",
                        posterHandle = posterHandle.ifBlank { "anonymous" },
                    )
                    coroutineScope.launch {
                        db.eventDao().insert(event)
                        onPosted()
                        onBack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Post event")
            }
        }
    }
}
