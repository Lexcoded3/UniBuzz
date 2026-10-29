package com.unibuzz.campusapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val priceText: String,
    val condition: String,
    val category: String,
    val locationHint: String,
    val contact: String,
    val imageUris: String,
    val status: String,
    val posterHandle: String,
    val createdAt: Long = System.currentTimeMillis(),
    val campusTag: String = "unibuzz"
)
