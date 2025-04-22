package com.example.stageconnect.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stageconnect.data.dummy.DummyDataProvider
import com.example.stageconnect.data.model.Offer
import com.example.stageconnect.domain.usecases.FilterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OfferViewModel @Inject constructor(
    private val filterUseCase: FilterUseCase
) : ViewModel()  {
    private val _offers = MutableLiveData<List<Offer>>(DummyDataProvider.offers)
    val offers: LiveData<List<Offer>> get() = _offers

    private val _filteredOffers = MutableLiveData<List<Offer>>(DummyDataProvider.offers)
    val filteredOffers: LiveData<List<Offer>> get() = _filteredOffers

    fun applyFilter(searchedText: String) {
        if(searchedText.isEmpty()){
            _filteredOffers.value = _offers.value
        }else{
            val filtered = filterUseCase.filterOffers(_offers.value ?: emptyList(), searchedText)
            _filteredOffers.value = filtered
        }
    }
}
