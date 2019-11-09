package com.apo.template.data.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CategoryEntity::class],
    version = 1
)
abstract class GotDatabase : RoomDatabase() {
    abstract fun categoriesDao(): CategoriesDAO
}