package com.envios.kitabisa.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.envios.kitabisa.data.local.dao.FavoriteDao
import com.envios.kitabisa.data.local.model.Favorite

@Database(entities = [Favorite::class], version = 1 , exportSchema = false)
abstract class FavoriteDb : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao

    companion object {

        @Volatile
        private var INSTANCE: FavoriteDb? = null

        fun getDatabase(context: Context): FavoriteDb? {
            if (INSTANCE == null) {
                synchronized(FavoriteDb::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            FavoriteDb::class.java, "message_database"
                        )
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}