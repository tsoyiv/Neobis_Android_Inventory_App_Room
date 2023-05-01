package com.example.shop_app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shoe_table")
data class Shoe(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val price: String,
    val distributor: String,
    val amount: String
)