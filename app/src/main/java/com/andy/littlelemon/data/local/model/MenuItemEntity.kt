package com.andy.littlelemon.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MenuItemEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val category: String,
    val imageUrl: String
)