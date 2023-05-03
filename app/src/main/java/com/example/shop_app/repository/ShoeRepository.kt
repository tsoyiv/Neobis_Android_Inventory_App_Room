package com.example.shop_app.repository

import androidx.lifecycle.LiveData
import com.example.shop_app.data.ShoeDao
import com.example.shop_app.model.Shoe

class ShoeRepository(private val shoeDao: ShoeDao) {

    val readAllData: LiveData<List<Shoe>> = shoeDao.readAllData()

    suspend fun addShoe(shoe: Shoe){
        shoeDao.addShoe(shoe)
    }
}