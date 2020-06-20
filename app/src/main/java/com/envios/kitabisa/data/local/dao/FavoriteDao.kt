package com.envios.kitabisa.data.local.dao

import androidx.room.*
import com.envios.kitabisa.data.local.model.Favorite

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: Favorite)


    @Query("SELECT * FROM favorite_table")
    fun getAllFavorites(): List<Favorite?>

    @Query("DELETE FROM favorite_table WHERE id = :id")
    fun delete(id:Int)

    @Query("SELECT * FROM favorite_table WHERE id = :id")
    fun checkIsFavoriteMovie(id:Int):Favorite?
}