package com.example.shop_app.viewmodel

import android.app.Application
import com.example.shop_app.model.Shoe
import com.example.shop_app.presenter.ShoePresenter
import java.util.concurrent.Executors

class ArchiveViewModel(private val repository: ShoePresenter) :
    ShoeViewModel(application = Application()) {
    //val archiveShoes = repository.readAllData(true)

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
                    true
                )
            )
        }
        thread.shutdown()
    }
}