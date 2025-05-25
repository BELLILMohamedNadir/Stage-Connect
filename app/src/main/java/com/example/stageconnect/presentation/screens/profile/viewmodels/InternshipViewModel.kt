package com.example.stageconnect.presentation.screens.profile.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stageconnect.data.dtos.InternshipDto
import com.example.stageconnect.data.remote.repository.StorageRepository
import com.example.stageconnect.domain.CONSTANT.USER_ID
import com.example.stageconnect.domain.result.Result
import com.example.stageconnect.domain.usecases.create.CreateInternshipUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class InternshipViewModel @Inject constructor(
    private val createInternshipUseCase: CreateInternshipUseCase,
    private val storageRepository: StorageRepository,
) : ViewModel() {

    private val _createInternshipResult = MutableLiveData<Result<InternshipDto>>()
    val createInternshipResult: LiveData<Result<InternshipDto>> get() = _createInternshipResult


    fun createInternship(request: InternshipDto) {
        request.userId = storageRepository.get(label = USER_ID)!!.toLong()
        viewModelScope.launch {
            _createInternshipResult.postValue(Result.Loading())
            delay(100)
            try {
                val result = createInternshipUseCase.execute(request)
                _createInternshipResult.postValue(Result.Success(result))
            } catch (e: Exception) {
                _createInternshipResult.postValue(Result.Error(e))
            }
        }
    }

}
