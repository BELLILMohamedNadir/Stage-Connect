package com.example.stageconnect.presentation.screens.profile.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stageconnect.data.dtos.CertificationDto
import com.example.stageconnect.data.remote.repository.StorageRepository
import com.example.stageconnect.domain.CONSTANT.USER_ID
import com.example.stageconnect.domain.result.Result
import com.example.stageconnect.domain.usecases.create.CreateCertificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CertificationViewModel @Inject constructor(
    private val createCertificationUseCase: CreateCertificationUseCase,
    private val storageRepository: StorageRepository,
) : ViewModel() {

    private val _createCertificationResult = MutableLiveData<Result<CertificationDto>>()
    val createCertificationResult: LiveData<Result<CertificationDto>> get() = _createCertificationResult


    fun createCertification(request: CertificationDto) {
        request.userId = storageRepository.get(label = USER_ID)!!.toLong()
        viewModelScope.launch {
            _createCertificationResult.postValue(Result.Loading())
            delay(100)
            try {
                val result = createCertificationUseCase.execute(request)
                _createCertificationResult.postValue(Result.Success(result))
            } catch (e: Exception) {
                _createCertificationResult.postValue(Result.Error(e))
            }
        }
    }

}
