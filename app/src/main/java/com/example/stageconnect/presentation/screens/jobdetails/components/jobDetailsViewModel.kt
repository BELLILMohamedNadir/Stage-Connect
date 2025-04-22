package com.example.stageconnect.presentation.screens.jobdetails.components

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.stageconnect.data.model.Offer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class JobDetailsViewModel @Inject constructor() : ViewModel() {
    private val _offer = mutableStateOf<Offer?>(null)
    val offer: State<Offer?> = _offer

    fun setOffer(newOffer: Offer) {
        _offer.value = newOffer
    }
}
