package com.example.shop_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shop_app.presenter.ShoeRepository

class ShoeViewModelFactory(private val repository: ShoeRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShoeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShoeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}