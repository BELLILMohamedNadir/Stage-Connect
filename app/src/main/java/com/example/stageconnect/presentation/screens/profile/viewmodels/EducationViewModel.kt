package com.example.stageconnect.presentation.screens.profile.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stageconnect.data.dtos.CertificationDto
import com.example.stageconnect.data.dtos.EducationDto
import com.example.stageconnect.data.remote.repository.StorageRepository
import com.example.stageconnect.domain.CONSTANT.USER_ID
import com.example.stageconnect.domain.result.Result
import com.example.stageconnect.domain.usecases.certification.DeleteCertificationUseCase
import com.example.stageconnect.domain.usecases.certification.GetAllCertificationUseCase
import com.example.stageconnect.domain.usecases.certification.UpdateCertificationUseCase
import com.example.stageconnect.domain.usecases.education.CreateEducationUseCase
import com.example.stageconnect.domain.usecases.education.DeleteEducationUseCase
import com.example.stageconnect.domain.usecases.education.GetAllEducationUseCase
import com.example.stageconnect.domain.usecases.education.UpdateEducationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EducationViewModel @Inject constructor(
    private val createEducationUseCase: CreateEducationUseCase,
    private val storageRepository: StorageRepository,
    private val deleteEducationUseCase: DeleteEducationUseCase,
    private val getAllEducationUseCase: GetAllEducationUseCase,
    private val updateEducationUseCase: UpdateEducationUseCase
) : ViewModel() {

    private val _data = MutableLiveData<MutableList<EducationDto>>()
    val data: LiveData<MutableList<EducationDto>> get() = _data

    private val _createEducationResult = MutableLiveData<Result<EducationDto>>()
    val createEducationResult: LiveData<Result<EducationDto>> get() = _createEducationResult

    private val _deleteEducationResult = MutableLiveData<Result<Unit>>()
    val deleteEducationResult: LiveData<Result<Unit>> get() = _deleteEducationResult

    private val _updateEducationResult = MutableLiveData<Result<EducationDto>>()
    val updateEducationResult: LiveData<Result<EducationDto>> get() = _updateEducationResult

    private val _getAllEducationResult = MutableLiveData<Result<List<EducationDto>>>()
    val getAllEducationResult: LiveData<Result<List<EducationDto>>> get() = _getAllEducationResult

    private val _education = mutableStateOf<EducationDto?>(value = null)

    private val _deleteEducation = MutableLiveData<Boolean?>(false)
    val deleteEducation: LiveData<Boolean?> get() = _deleteEducation

    fun createEducation(request: EducationDto) {
        request.userId = storageRepository.get(label = USER_ID)!!.toLong()
        viewModelScope.launch {
            _createEducationResult.postValue(Result.Loading())
            delay(100)
            try {
                val result = createEducationUseCase.execute(request)
                _createEducationResult.postValue(Result.Success(result))
                clearData()
            } catch (e: Exception) {
                _createEducationResult.postValue(Result.Error(e))
            }
        }
    }

    fun getAllEducation() {
        val userId = storageRepository.get(label = USER_ID)!!.toLong()
        viewModelScope.launch {
            _getAllEducationResult.postValue(Result.Loading())
            delay(100)
            try {
                val result = getAllEducationUseCase.execute(userId)
                _getAllEducationResult.postValue(Result.Success(result))
                _data.value = result.toMutableList()
                clearData()
            } catch (e: Exception) {
                _getAllEducationResult.postValue(Result.Error(e))
            }
        }
    }

    fun deleteEducation(id: Long) {
        viewModelScope.launch {
            _deleteEducationResult.postValue(Result.Loading())
            delay(100)
            try {
                deleteEducationUseCase.execute(id)
                _deleteEducationResult.postValue(Result.Success(Unit))
                clearData()
            } catch (e: Exception) {
                _deleteEducationResult.postValue(Result.Error(e))
            }
        }
    }

    fun updateEducation(request: EducationDto) {
        viewModelScope.launch {
            _updateEducationResult.postValue(Result.Loading())
            delay(100)
            try {
                val result = updateEducationUseCase.execute(request.id!!, request)
                _updateEducationResult.postValue(Result.Success(result))
                clearData()
            } catch (e: Exception) {
                _updateEducationResult.postValue(Result.Error(e))
            }
        }
    }

    fun setEducation(educationDto: EducationDto?){
        this._education.value = educationDto
    }

    fun getEducation(): EducationDto?{
        return this._education.value
    }

    fun setDeleteEducation(){
        this._deleteEducation.value = true
    }

    fun clearData(){
        this._getAllEducationResult.value = null
        this._createEducationResult.value = null
        this._updateEducationResult.value = null
        this._deleteEducationResult.value = null
        this._deleteEducation.value = null
    }

}
