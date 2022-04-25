package com.example.winepairing.model.data




data class Wine(
    val pairedWines: List<String>?,
    val pairingText: String?,
    val productMatches: List<ProductMatches>?,
    val message: String?,
    val status: String?
)