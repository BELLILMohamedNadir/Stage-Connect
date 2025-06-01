package com.example.stageconnect.presentation.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stageconnect.data.dtos.OfferDto
import com.example.stageconnect.data.remote.repository.StorageRepository
import com.example.stageconnect.domain.CONSTANT.USER_ID
import com.example.stageconnect.domain.result.Result
import com.example.stageconnect.domain.usecases.filter.FilterUseCase
import com.example.stageconnect.domain.usecases.offer.SaveOfferUseCase
import com.example.stageconnect.domain.usecases.offer.UnSaveOfferUseCase
import com.example.stageconnect.domain.usecases.offer.CreateOfferUseCase
import com.example.stageconnect.domain.usecases.offer.GetAllOffersUseCase
import com.example.stageconnect.domain.usecases.offer.GetAllRecruiterOffersUseCase
import com.example.stageconnect.domain.usecases.offer.GetAllSavedOffersUseCase
import com.example.stageconnect.domain.usecases.offer.UpdateOfferUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfferViewModel @Inject constructor(
    private val storageRepository: StorageRepository,
    private val filterUseCase: FilterUseCase,
    private val getAllOffersUseCase: GetAllOffersUseCase,
    private val getAllRecruiterOffersUseCase: GetAllRecruiterOffersUseCase,
    private val getAllSavedOffersUseCase: GetAllSavedOffersUseCase,
    private val saveOfferUseCase: SaveOfferUseCase,
    private val unSaveOfferUseCase: UnSaveOfferUseCase,
    private val createOfferUseCase: CreateOfferUseCase,
    private val updateOfferUseCase: UpdateOfferUseCase
) : ViewModel() {
    private val _offers = MutableLiveData<MutableList<OfferDto>>()
    val offers: LiveData<MutableList<OfferDto>> get() = _offers

    private val _filteredOffers = MutableLiveData<List<OfferDto>>(emptyList())
    val filteredOffers: LiveData<List<OfferDto>> get() = _filteredOffers

    private val _getAllOfferResult = MutableLiveData<Result<List<OfferDto>>?>()
    val getAllOfferResult: LiveData<Result<List<OfferDto>>?> get() = _getAllOfferResult

    private val _getAllRecruiterOfferResult = MutableLiveData<Result<List<OfferDto>>?>()
    val getAllRecruiterOfferResult: LiveData<Result<List<OfferDto>>?> get() = _getAllRecruiterOfferResult

    private val _getAllSavedOfferResult = MutableLiveData<Result<List<OfferDto>>?>()
    val getAllSavedOfferResult: LiveData<Result<List<OfferDto>>?> get() = _getAllSavedOfferResult

    private val _saveOfferResult = MutableLiveData<Result<OfferDto?>?>()
    val saveOfferResult: LiveData<Result<OfferDto?>?> get() = _saveOfferResult

    private val _createOfferResult = MutableLiveData<Result<OfferDto?>?>()
    val createOfferResult: LiveData<Result<OfferDto?>?> get() = _createOfferResult

    private val _updateOfferResult = MutableLiveData<Result<OfferDto?>?>()
    val updateOfferResult: LiveData<Result<OfferDto?>?> get() = _updateOfferResult
    private val _selectedOffer = mutableStateOf<OfferDto?>(null)
    val selectedOffer: State<OfferDto?> = _selectedOffer

    fun selectOffer(offer: OfferDto) {
        _selectedOffer.value = offer
    }
    fun getAllOffer() {
        val studentId = storageRepository.get(USER_ID)!!.toLong()
        viewModelScope.launch {
            _getAllOfferResult.postValue(Result.Loading())
            delay(300)
            try {
                val result = getAllOffersUseCase.execute(studentId)
                _offers.value = result.toMutableList()
                _filteredOffers.value = _offers.value!!.shuffled()
                _getAllOfferResult.postValue(Result.Success(result))
//                clearDate()
            } catch (e: Exception) {
                _getAllOfferResult.postValue(Result.Error(e))
            }
        }
    }

    fun getAllRecruiterOffer() {
        val recruiterId = storageRepository.get(USER_ID)!!.toLong()
        viewModelScope.launch {
            _getAllRecruiterOfferResult.postValue(Result.Loading())
            delay(300)
            try {
                val result = getAllRecruiterOffersUseCase.execute(recruiterId)
                _offers.value = result.toMutableList()
                _filteredOffers.value = _offers.value
                _getAllRecruiterOfferResult.postValue(Result.Success(result))
//                clearDate()
            } catch (e: Exception) {
                _getAllRecruiterOfferResult.postValue(Result.Error(e))
            }
        }
    }

    fun getAllSavedOffer() {
        val studentId = storageRepository.get(USER_ID)!!.toLong()
        viewModelScope.launch {
            _getAllSavedOfferResult.postValue(Result.Loading())
            delay(300)
            try {
                val result = getAllSavedOffersUseCase.execute(studentId)
                _offers.value = result.toMutableList()
                _getAllSavedOfferResult.postValue(Result.Success(result))
                _filteredOffers.value = _offers.value
//                clearDate()
            } catch (e: Exception) {
                _getAllSavedOfferResult.postValue(Result.Error(e))
            }
        }
    }

    fun applyFilter(searchedText: String, selectedFilters: Map<String, List<String>>) {
        if (searchedText.isEmpty()) {
            _filteredOffers.value = _offers.value
        } else {
            val filtered = filterUseCase.filterOffers(_offers.value ?: emptyList(), searchedText, selectedFilters)
            _filteredOffers.value = filtered
        }
    }

    fun applyFilter(filter: String) {
        if (filter.isEmpty() || filter == "All") {
            _filteredOffers.value = _offers.value
        } else {
            val filtered = filterUseCase.filterOffers(_offers.value ?: emptyList(), filter)
            _filteredOffers.value = filtered
        }
    }

    fun saveOffer(
        offerDto: OfferDto,
        savedOfferMessage: String = "",
        unSavedOfferMessage: String = "",
        context: Context
    ) {
        val studentId = storageRepository.get(USER_ID)!!.toLong()
        viewModelScope.launch {
            _saveOfferResult.postValue(Result.Loading())
            delay(100)
            try {
                if (offerDto.isSaved) {
                    unSaveOfferUseCase.execute(offerId = offerDto.id!!, studentId = studentId)
                    offerDto.isSaved = false
                    Toast.makeText(context, unSavedOfferMessage, Toast.LENGTH_SHORT).show()
                } else {
                    saveOfferUseCase.execute(offerId = offerDto.id!!, studentId = studentId)
                    offerDto.isSaved = true
                    Toast.makeText(context, savedOfferMessage, Toast.LENGTH_SHORT).show()
                }
                _saveOfferResult.postValue(Result.Success(offerDto))
                _offers.value = _offers.value?.map { offer ->
                    if (offer.id == offerDto.id) offerDto else offer
                }?.toMutableList()
                _filteredOffers.value = _offers.value
                //                clearDate()
            } catch (e: Exception) {
                _saveOfferResult.postValue(Result.Error(e))
            }
        }
    }

    fun saveOnlySavedOffers() {
        _offers.value = _offers.value?.filter {
            it.isSaved
        }?.toMutableList()
        _filteredOffers.value = _offers.value
    }

    fun createOffer(offerDto: OfferDto) {
        val recruiterId = storageRepository.get(USER_ID)!!.toLong()
        offerDto.recruiterId = recruiterId
        viewModelScope.launch {
            _createOfferResult.postValue(Result.Loading())
            delay(100)
            try {
                val result = createOfferUseCase.execute(offerDto)
                _createOfferResult.postValue(Result.Success(result))

                //                clearDate()
            } catch (e: Exception) {
                _createOfferResult.postValue(Result.Error(e))
            }
        }
    }

    fun updateOffer(offerDto: OfferDto, offerId: Long) {
        viewModelScope.launch {
            _updateOfferResult.postValue(Result.Loading())
            delay(100)
            try {
                val updatedOffer = updateOfferUseCase.execute(offerId, offerDto)
                _updateOfferResult.postValue(Result.Success(updatedOffer))
                _offers.value = _offers.value?.map { offer ->
                    if (offer.id == updatedOffer.id) updatedOffer else offer
                }?.toMutableList()
                _filteredOffers.value = _offers.value
                //                clearDate()
            } catch (e: Exception) {
                _updateOfferResult.postValue(Result.Error(e))
            }
        }
    }

    fun clearDate() {
        _getAllOfferResult.value = null
        _saveOfferResult.value = null
    }
}
