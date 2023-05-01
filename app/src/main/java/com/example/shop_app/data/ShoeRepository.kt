package com.example.shop_app.data

import androidx.lifecycle.LiveData

class ShoeRepository(private val shoeDao: ShoeDao) {

    val readAllData: LiveData<List<Shoe>> = shoeDao.readAllData()

    suspend fun addShoe(shoe: Shoe){
        shoeDao.addShoe(shoe)
    }
}