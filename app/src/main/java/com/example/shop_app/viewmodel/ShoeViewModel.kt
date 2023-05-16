package com.example.shop_app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.shop_app.data.ShoeDatabase
import com.example.shop_app.presenter.ShoePresenter
import com.example.shop_app.model.Shoe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShoeViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Shoe>>
    private val presenter: ShoePresenter

    init {
        val shoeDao = ShoeDatabase.getDatabase(application).shoeDao()
        presenter = ShoePresenter(shoeDao)
        readAllData = presenter.readAllData
    }
    fun addShoe(shoe: Shoe) {
        viewModelScope.launch( Dispatchers.IO) {
            presenter.addShoe(shoe)
        }
    }
    fun searchDatabase(searchQuery: String): LiveData<List<Shoe>> {
        return presenter.searchDatabase(searchQuery)
    }
    fun updateShoe(shoe: Shoe) {
        viewModelScope.launch (Dispatchers.IO){
            presenter.updateShoe(shoe)
        }
    }
    fun deleteShoe(shoe: Shoe) {
        viewModelScope.launch(Dispatchers.IO) {
            presenter.deleteShoe(shoe)
        }
    }
    fun deleteAllShoe(currentShoe: Shoe?) {
        viewModelScope.launch(Dispatchers.IO) {
            presenter.deleteAllShoe()
        }
    }
}