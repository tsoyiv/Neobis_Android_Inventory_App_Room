package com.example.shop_app.model

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "shoe_table")
//@TypeConverters(Converter::class)
data class Shoe(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val price: String,
    val distributor: String,
    val amount: String,
    //val isArchived: Boolean
    val shoeImage: Bitmap
): Parcelable