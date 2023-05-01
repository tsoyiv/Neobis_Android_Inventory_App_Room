package com.example.shop_app.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShoeViewModel(application: Application): AndroidViewModel(application) {

    private val readAllData: LiveData<List<Shoe>>
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