package com.example.shop_app.repository

import androidx.lifecycle.LiveData
import androidx.room.Query
import com.example.shop_app.data.ShoeDao
import com.example.shop_app.model.Shoe
import kotlinx.coroutines.flow.Flow

class ShoeRepository(private val shoeDao: ShoeDao) {

    val readAllData: LiveData<List<Shoe>> = shoeDao.readAllData()

    suspend fun addShoe(shoe: Shoe){
        shoeDao.addShoe(shoe)
    }

    fun searchDatabase(searchQuery: String): Flow<List<Shoe>> {
        return shoeDao.searchDatabase(searchQuery)
    }

    fun updateShoe(shoe: Shoe) {
        shoeDao.updateShoe(shoe)
    }
}