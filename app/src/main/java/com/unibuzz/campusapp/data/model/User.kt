package com.unibuzz.campusapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val fullName: String,
    val email: String,
    val handle: String,
    val password: String,
    val campusTag: String = "unibuzz",
    val avatarUrl: String = "",
    val createdAt: Long = System.currentTimeMillis(),
)
