package com.example.winepairing.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.winepairing.BuildConfig
import com.example.winepairing.model.data.ProductMatches
import com.example.winepairing.model.data.Wine
import com.example.winepairing.model.data.WineDao
import com.example.winepairing.model.network.WineApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


const val CLIENT_ID = BuildConfig.SPOONACULAR_ACCESS_KEY

class PairingsViewModel(val wineDao: WineDao) : ViewModel() {
    private val _apiResponse = MutableLiveData<Wine>()
    val apiResponse: LiveData<Wine> = _apiResponse
    private var onSaveClick: ((ProductMatches) -> Unit)? = null

    fun getWinePairings(food: String) {
        viewModelScope.launch {
            _apiResponse.value = WineApi.retrofitService.getWinePairing(food, CLIENT_ID)

        }
    }

    suspend fun insert(productMatches: List<ProductMatches>?) {
        wineDao.insert(productMatches)
    }

    fun wineExists(id: Int): Boolean {
        return wineDao.wineExists(id)
    }

    suspend fun onChecked(productMatches: List<ProductMatches>?, id: Int) {
        if (!wineExists(id)) {
            insert(productMatches)
        }
    }

    fun showSavedWines(): Flow<List<ProductMatches>> = wineDao.showSavedWines()



    fun onSaveClickListener(listener: (ProductMatches) -> Unit) {
        onSaveClick = listener

    }
}