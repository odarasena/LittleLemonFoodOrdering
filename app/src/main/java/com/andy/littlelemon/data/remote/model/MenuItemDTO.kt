package com.andy.littlelemon.data.remote.model

import com.andy.littlelemon.data.local.model.MenuItemEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MenuItemDTO(
    @SerialName("id")
    val id: Int,

    @SerialName("title")
    val title: String,

    @SerialName("description")
    val description: String,

    @SerialName("price")
    val price: Double,

    @SerialName("category")
    val category: String,

    @SerialName("image")
    val imageUrl: String
)

fun MenuItemDTO.convertToMenuItemEntity() = MenuItemEntity(
    id = id,
    title = title,
    description = description,
    price = price,
    category = category,
    imageUrl = imageUrl
)