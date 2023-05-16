package com.example.shop_app.presenter

import androidx.lifecycle.LiveData
import com.example.shop_app.data.ShoeDao
import com.example.shop_app.model.Shoe

class ShoeRepository(private val shoeDao: ShoeDao) {

    fun readAllData(isArchived: Boolean): LiveData<List<Shoe>> {
        return shoeDao.readAllData(isArchived)
    }

//    fun readAllData(isArchived: Boolean) : LiveData<List<Shoe>> {
//        return shoeDao.readAllData(isArchived)
//    }

    fun addShoe(shoe: Shoe) {
        shoeDao.addShoe(shoe)
    }

    fun searchDatabase(searchQuery: String): LiveData<List<Shoe>> {
        return shoeDao.searchDatabase(searchQuery)
    }

    fun updateShoe(shoe: Shoe) {
        shoeDao.updateShoe(shoe)
    }

    suspend fun deleteShoe(shoe: Shoe) {
        shoeDao.deleteShoe(shoe)
    }

    suspend fun deleteAllShoe() {
        shoeDao.deleteAllShoe()
    }
    fun getAllSearchArchiveProduct(searchWord: String) : LiveData<List<Shoe>> {
        return shoeDao.searchDatabase(searchWord)
    }
}