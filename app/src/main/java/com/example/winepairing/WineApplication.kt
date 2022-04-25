package com.example.winepairing

import android.app.Application
import com.example.winepairing.model.data.WineDatabase

class WineApplication: Application() {
    val database: WineDatabase by lazy { WineDatabase.getInstance(this) }
}