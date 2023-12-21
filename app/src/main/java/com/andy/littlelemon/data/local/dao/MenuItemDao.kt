package com.andy.littlelemon.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.andy.littlelemon.data.local.model.MenuItemEntity

@Dao
interface MenuItemDao {

    @Query("SELECT * FROM MenuItemEntity WHERE LOWER(category)=LOWER(:category) AND LOWER(title) LIKE LOWER('%' || :searchString || '%')")
    suspend fun getAllFromCategory(category: String, searchString: String): List<MenuItemEntity>

    @Insert
    fun insertAll(menuItems: List<MenuItemEntity>)

    @Query("SELECT (SELECT COUNT(*) FROM MenuItemEntity) == 0")
    fun isEmpty(): Boolean
}