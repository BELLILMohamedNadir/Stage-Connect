package com.example.stageconnect.presentation.screens.profile.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stageconnect.data.dtos.CertificationDto
import com.example.stageconnect.data.dtos.EducationDto
import com.example.stageconnect.data.remote.repository.StorageRepository
import com.example.stageconnect.domain.CONSTANT.USER_ID
import com.example.stageconnect.domain.result.Result
import com.example.stageconnect.domain.usecases.create.CreateEducationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EducationViewModel @Inject constructor(
    private val createEducationUseCase: CreateEducationUseCase,
    private val storageRepository: StorageRepository,
) : ViewModel() {

    private val _createEducationResult = MutableLiveData<Result<EducationDto>>()
    val createEducationResult: LiveData<Result<EducationDto>> get() = _createEducationResult


    fun createEducation(request: EducationDto) {
        request.userId = storageRepository.get(label = USER_ID)!!.toLong()
        viewModelScope.launch {
            _createEducationResult.postValue(Result.Loading())
            delay(100)
            try {
                val result = createEducationUseCase.execute(request)
                _createEducationResult.postValue(Result.Success(result))
            } catch (e: Exception) {
                _createEducationResult.postValue(Result.Error(e))
            }
        }
    }

}
