package com.example.winepairing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.winepairing.model.data.WineDao

class PairingsViewModelFactory(private val wineDao: WineDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PairingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PairingsViewModel(wineDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}