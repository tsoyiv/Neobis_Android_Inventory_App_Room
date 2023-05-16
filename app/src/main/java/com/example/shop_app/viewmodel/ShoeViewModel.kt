package com.example.shop_app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shop_app.data.ShoeDatabase
import com.example.shop_app.presenter.ShoeRepository
import com.example.shop_app.model.Shoe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

open class ShoeViewModel(private val repository: ShoeRepository): ViewModel() {

    val isDataSaved = MutableLiveData<Boolean?>()
    private val thread = Executors.newSingleThreadExecutor()

    fun getAllData(isArchived: Boolean): LiveData<List<Shoe>> {
        return repository.readAllData(isArchived)
    }

    fun updateData(product: Shoe) {
        thread.execute {
            repository.updateShoe(product)
        }

        thread.shutdown()
        isDataSaved.postValue(thread.isShutdown)
    }
    fun getAllSearchProduct(searchWord: String): LiveData<List<Shoe>> {
        return repository.searchDatabase(searchWord)
    }

//    val readAllData: LiveData<List<Shoe>>
//    private val presenter: ShoeRepository
//
//    fun getAllData(isArchived: Boolean): LiveData<List<Shoe>> {
//        return presenter.readAllData(isArchived)
//    }
//
//    init {
//        val shoeDao = ShoeDatabase.getDatabase(application).shoeDao()
//        presenter = ShoeRepository(shoeDao)
//        readAllData = presenter.readAllData(isArchived = true)
//    }
//    fun addShoe(shoe: Shoe) {
//        viewModelScope.launch( Dispatchers.IO) {
//            presenter.addShoe(shoe)
//        }
//    }
////    fun getAllData(isArchived: Boolean): LiveData<List<Shoe>> {
////        return presenter.readAllData(isArchived)
////    }
//    fun searchDatabase(searchQuery: String): LiveData<List<Shoe>> {
//        return presenter.searchDatabase(searchQuery)
//    }
//    fun updateShoe(shoe: Shoe) {
//        viewModelScope.launch (Dispatchers.IO){
//            presenter.updateShoe(shoe)
//        }
//    }
//    fun deleteShoe(shoe: Shoe) {
//        viewModelScope.launch(Dispatchers.IO) {
//            presenter.deleteShoe(shoe)
//        }
//    }
//    fun deleteAllShoe(currentShoe: Shoe?) {
//        viewModelScope.launch(Dispatchers.IO) {
//            presenter.deleteAllShoe()
//        }
//    }
}