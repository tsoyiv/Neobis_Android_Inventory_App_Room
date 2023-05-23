package com.example.shop_app.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shop_app.model.Shoe
import com.example.shop_app.presenter.ShoeRepository
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

class ArchiveViewModel(private val repository: ShoeRepository): ViewModel() {
    val archiveShoes = repository.readAllData(true)

    fun getSearchArchiveProduct(searchProduct: String): LiveData<List<Shoe>> {
        return repository.getAllSearchArchiveProduct(searchProduct)
    }

    fun deleteProduct(id: Int) {
        viewModelScope.launch {
            repository.deleteProdShoe(id)
        }
    }

    fun unArchiveData(shoe: Shoe) {
        val thread = Executors.newSingleThreadExecutor()
        thread.execute {
            repository.updateShoe(
                Shoe(
                    shoe.id,
                    shoe.name,
                    shoe.price,
                    shoe.distributor,
                    shoe.amount,
                    shoe.shoeImage,
                    false
                )
            )
        }
        thread.shutdown()
    }
}