package com.example.shop_app.data

import android.app.Application
import com.example.shop_app.presenter.ShoeRepository

class ShoeApplication: Application() {
    private val database by lazy { ShoeDatabase.getDatabase(this) }
    val repository by lazy { ShoeRepository(database.shoeDao()) }
}