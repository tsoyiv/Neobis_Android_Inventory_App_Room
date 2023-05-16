package com.example.shop_app.presenter

import androidx.lifecycle.LiveData
import com.example.shop_app.data.ShoeDao
import com.example.shop_app.model.Shoe
import kotlinx.coroutines.flow.Flow

class ShoePresenter(private val shoeDao: ShoeDao) {

    val readAllData: LiveData<List<Shoe>> = shoeDao.readAllData()

    suspend fun addShoe(shoe: Shoe){
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
}