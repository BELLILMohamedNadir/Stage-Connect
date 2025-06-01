package com.example.stageconnect.presentation.screens.profile.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stageconnect.data.dtos.CertificationDto
import com.example.stageconnect.data.dtos.WorkExperienceDto
import com.example.stageconnect.data.remote.repository.StorageRepository
import com.example.stageconnect.domain.CONSTANT.USER_ID
import com.example.stageconnect.domain.result.Result
import com.example.stageconnect.domain.usecases.certification.CreateCertificationUseCase
import com.example.stageconnect.domain.usecases.certification.DeleteCertificationUseCase
import com.example.stageconnect.domain.usecases.certification.GetAllCertificationUseCase
import com.example.stageconnect.domain.usecases.certification.UpdateCertificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CertificationViewModel @Inject constructor(
    private val createCertificationUseCase: CreateCertificationUseCase,
    private val storageRepository: StorageRepository,
    private val deleteCertificationUseCase: DeleteCertificationUseCase,
    private val getAllCertificationUseCase: GetAllCertificationUseCase,
    private val updateCertificationUseCase: UpdateCertificationUseCase
) : ViewModel() {

    private val _data = MutableLiveData<MutableList<CertificationDto>>()
    val data: LiveData<MutableList<CertificationDto>> get() = _data

    private val _createCertificationResult = MutableLiveData<Result<CertificationDto>>()
    val createCertificationResult: LiveData<Result<CertificationDto>> get() = _createCertificationResult

    private val _deleteCertificationResult = MutableLiveData<Result<Unit>>()
    val deleteCertificationResult: LiveData<Result<Unit>> get() = _deleteCertificationResult

    private val _updateCertificationResult = MutableLiveData<Result<CertificationDto>>()
    val updateCertificationResult: LiveData<Result<CertificationDto>> get() = _updateCertificationResult

    private val _getAllCertificationResult = MutableLiveData<Result<List<CertificationDto>>>()
    val getAllCertificationResult: LiveData<Result<List<CertificationDto>>> get() = _getAllCertificationResult

    private val _certification = mutableStateOf<CertificationDto?>(value = null)

    private val _deleteCertification = MutableLiveData<Boolean?>(false)
    val deleteCertification: LiveData<Boolean?> get() = _deleteCertification

    fun createCertification(request: CertificationDto) {
        request.userId = storageRepository.get(label = USER_ID)!!.toLong()
        viewModelScope.launch {
            _createCertificationResult.postValue(Result.Loading())
            delay(100)
            try {
                val result = createCertificationUseCase.execute(request)
                _createCertificationResult.postValue(Result.Success(result))
                clearData()
            } catch (e: Exception) {
                _createCertificationResult.postValue(Result.Error(e))
            }
        }
    }

    fun getAllCertification() {
        val userId = storageRepository.get(label = USER_ID)!!.toLong()
        viewModelScope.launch {
            _getAllCertificationResult.postValue(Result.Loading())
            delay(100)
            try {
                val result = getAllCertificationUseCase.execute(userId)
                _data.value = result.toMutableList()
                _getAllCertificationResult.postValue(Result.Success(result))
                clearData()
            } catch (e: Exception) {
                _getAllCertificationResult.postValue(Result.Error(e))
            }
        }
    }

    fun deleteCertification(id: Long) {
        viewModelScope.launch {
            _deleteCertificationResult.postValue(Result.Loading())
            delay(100)
            try {
                deleteCertificationUseCase.execute(id)
                _deleteCertificationResult.postValue(Result.Success(Unit))
                clearData()
            } catch (e: Exception) {
                _deleteCertificationResult.postValue(Result.Error(e))
            }
        }
    }

    fun updateCertification(request: CertificationDto) {
        viewModelScope.launch {
            _updateCertificationResult.postValue(Result.Loading())
            delay(100)
            try {
                val result = updateCertificationUseCase.execute(request.id!!, request)
                _updateCertificationResult.postValue(Result.Success(result))
                clearData()
            } catch (e: Exception) {
                _updateCertificationResult.postValue(Result.Error(e))
            }
        }
    }

    fun setCertification(certificationDto: CertificationDto?){
        this._certification.value = certificationDto
    }

    fun getCertification(): CertificationDto?{
        return this._certification.value
    }

    fun setDeleteCertification(){
        this._deleteCertification.value = true
    }

    fun clearData(){
        this._getAllCertificationResult.value = null
        this._createCertificationResult.value = null
        this._updateCertificationResult.value = null
        this._deleteCertificationResult.value = null
        this._deleteCertification.value = null
    }
}
