package com.example.stageconnect.presentation.screens.offer.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.stageconnect.data.dtos.OfferDto
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class JobDetailsViewModel @Inject constructor() : ViewModel() {
    private val _offer = mutableStateOf<OfferDto?>(null)
    val offer: State<OfferDto?> = _offer

    private val _source = mutableStateOf<String>("")
    val source: State<String> = _source

    fun setOffer(newOffer: OfferDto?) {
        _offer.value = newOffer
    }

    fun setSource(newSource: String) {
        _source.value = newSource
    }
}
