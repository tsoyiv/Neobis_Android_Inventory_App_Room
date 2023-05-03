package com.example.shop_app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.shop_app.data.ShoeDatabase
import com.example.shop_app.repository.ShoeRepository
import com.example.shop_app.model.Shoe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShoeViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Shoe>>
    private val repository: ShoeRepository

    init {
        val shoeDao = ShoeDatabase.getDatabase(application).shoeDao()
        repository = ShoeRepository(shoeDao)
        readAllData = repository.readAllData
    }
    fun addShoe(shoe: Shoe) {
        viewModelScope.launch( Dispatchers.IO) {
            repository.addShoe(shoe)
        }
    }
}