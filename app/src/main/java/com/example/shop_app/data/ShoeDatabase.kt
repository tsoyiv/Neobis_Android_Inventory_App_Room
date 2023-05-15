package com.example.shop_app.data

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import androidx.room.*
import com.example.shop_app.model.Shoe
import com.example.shop_app.presenter.ConverterImage

@Database(entities = [Shoe::class], version = 1, exportSchema = false)
@TypeConverters(ConverterImage::class)
abstract class ShoeDatabase : RoomDatabase() {

    abstract fun shoeDao(): ShoeDao

    companion object {
        @Volatile
        private var INSTANCE: ShoeDatabase? = null

        fun getDatabase(context: Context): ShoeDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ShoeDatabase::class.java,
                    "shoe_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}