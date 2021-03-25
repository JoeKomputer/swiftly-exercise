package com.example.swiftlylist.ui.main

import androidx.lifecycle.liveData
import com.example.swiftlylist.repositories.SpecialsRepo
import com.example.swiftlylist.ui.main.util.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ticker
import javax.inject.Inject

@HiltViewModel
class SpecialsViewModel @Inject constructor(private val specialsRepo: SpecialsRepo): BaseViewModel() {

    //poll api every 30 seconds to check for updates
    val tickerChannel = ticker(delayMillis = 30_000, initialDelayMillis = 0)
    val getProducts = liveData(Dispatchers.IO) {
        for (event in tickerChannel) {
            emit(specialsRepo.getProducts())
        }
    }

    override fun onCleared() {
        tickerChannel.cancel()
        super.onCleared()
    }
}