package com.example.androidmvi.repository.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "user"
)
data class UserModel(
    val userId: Int,
    @PrimaryKey
    val id: Int,
    val title: String,
    val completed: Boolean
)