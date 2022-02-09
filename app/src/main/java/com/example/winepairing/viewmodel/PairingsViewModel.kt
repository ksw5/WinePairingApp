package com.example.winepairing.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.winepairing.BuildConfig
import com.example.winepairing.model.data.Wine
import com.example.winepairing.model.network.WineApi
import kotlinx.coroutines.launch


const val CLIENT_ID = BuildConfig.SPOONACULAR_ACCESS_KEY

class PairingsViewModel : ViewModel() {
    private val _apiResponse = MutableLiveData<Wine>()
    val apiResponse: LiveData<Wine> = _apiResponse


    fun getWinePairings(food: String) {
        viewModelScope.launch {
            _apiResponse.value = WineApi.retrofitService.getWinePairing(food, CLIENT_ID)

        }
    }
}