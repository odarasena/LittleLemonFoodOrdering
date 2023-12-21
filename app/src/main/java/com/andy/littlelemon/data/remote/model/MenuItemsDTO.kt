package com.andy.littlelemon.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MenuItemsDTO(
    @SerialName("menu")
    val items: List<MenuItemDTO>
)