package com.example.shop_app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shop_app.model.Shoe
import com.example.shop_app.presenter.ShoeRepository
import java.util.concurrent.Executors

class AddViewModel (private val repository: ShoeRepository) : ViewModel() {
    val isDataSaved = MutableLiveData<Boolean?>()
    private val thread = Executors.newSingleThreadExecutor()

    fun addData(product: Shoe) {
        thread.execute {
            repository.addShoe(product)
        }
        thread.shutdown()
        isDataSaved.postValue(thread.isShutdown)
    }

    fun updateData(product: Shoe) {
        thread.execute {
            repository.updateShoe(product)
        }

        thread.shutdown()
        isDataSaved.postValue(thread.isShutdown)
    }
}
