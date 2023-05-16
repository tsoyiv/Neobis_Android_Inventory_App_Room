package com.example.shop_app.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import com.example.shop_app.model.Shoe

@Dao
interface ShoeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addShoe(shoe: Shoe)

    @Query("SELECT * FROM shoe_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Shoe>>

    @Query("SELECT * FROM shoe_table WHERE name LIKE :searchQuery OR distributor LIKE :searchQuery ORDER BY id ASC")
    fun searchDatabase(searchQuery: String): LiveData<List<Shoe>>

    @Query("SELECT * FROM shoe_table WHERE isArchived = :isArchived ORDER BY id ASC")
    fun readAllData(isArchived: Boolean): LiveData<List<Shoe>>

    @Update
    fun updateShoe(shoe: Shoe)

    @Delete
    suspend fun deleteShoe(shoe: Shoe)

    @Query("SELECT * FROM shoe_table WHERE name LIKE :searchQuery AND isArchived = :isArchived ORDER BY name ASC")
    fun getSearchArchiveProduct(
        searchQuery: String,
        isArchived: Boolean = true
    ): LiveData<List<Shoe>>

    @Query("DELETE FROM shoe_table")
    suspend fun deleteAllShoe()

}