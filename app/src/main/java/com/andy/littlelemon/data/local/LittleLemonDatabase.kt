package com.andy.littlelemon.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.andy.littlelemon.data.local.dao.MenuItemDao
import com.andy.littlelemon.data.local.model.MenuItemEntity

@Database(entities = [MenuItemEntity::class], version = 1)
abstract class LittleLemonDatabase : RoomDatabase() {

    abstract fun menuItemDao(): MenuItemDao

    companion object {
        lateinit var INSTANCE: LittleLemonDatabase

        fun buildDatabase(context: Context) {
            INSTANCE = Room.databaseBuilder(context, LittleLemonDatabase::class.java, "littlelemon.db").build()
        }
    }
}