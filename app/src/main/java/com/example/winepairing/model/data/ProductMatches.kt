package com.example.winepairing.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favoriteWines")
data class ProductMatches(
    val averageRating: Double?,
    val description: String?,
    @PrimaryKey
    val id: Int?,
    val imageUrl: String?,
    val link: String?,
    val price: String?,
    val ratingCount: Double?,
    val score: Double?,
    val title: String?
)