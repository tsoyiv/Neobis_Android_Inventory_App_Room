package com.example.shop_app

import com.example.shop_app.model.Shoe

interface RecyclerListener {
    fun archiveProduct(shoe: Shoe)
}