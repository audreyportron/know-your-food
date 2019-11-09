package com.apo.template.data.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Single

@Dao
interface CategoriesDAO {

    @Insert
    fun insertAll(categories: List<CategoryEntity>)

    @Query("SELECT * FROM category")
    fun getAll(): Single<List<CategoryEntity>>

    @Delete
    fun deleteAll(categories: List<CategoryEntity>)


}