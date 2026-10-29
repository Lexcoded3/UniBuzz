package com.unibuzz.campusapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class Event(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val dateTime: Long,
    val venue: String,
    val organizer: String,
    val contact: String,
    val imageUris: String,
    val type: String,
    val status: String,
    val posterHandle: String,
    val createdAt: Long = System.currentTimeMillis(),
    val campusTag: String = "unibuzz"
)
