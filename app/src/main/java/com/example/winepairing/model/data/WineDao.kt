package com.example.winepairing.model.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface WineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(productMatches: List<ProductMatches>?)

    @Delete
    suspend fun delete(productMatches: ProductMatches)

    @Query("SELECT EXISTS(SELECT * FROM favoriteWines WHERE id = :id)")
    fun wineExists(id:Int): Boolean

    @Query("SELECT * FROM favoriteWines")
    fun showSavedWines(): Flow<List<ProductMatches>>

}

