package com.example.stageconnect.presentation.screens.profile.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stageconnect.data.dtos.OfferDto
import com.example.stageconnect.data.dtos.WorkExperienceDto
import com.example.stageconnect.data.remote.repository.StorageRepository
import com.example.stageconnect.domain.CONSTANT.USER_ID
import com.example.stageconnect.domain.result.Result
import com.example.stageconnect.domain.usecases.workexperience.CreateWorkExperienceUseCase
import com.example.stageconnect.domain.usecases.workexperience.DeleteWorkExperienceUseCase
import com.example.stageconnect.domain.usecases.workexperience.GetAllWorkExperienceUseCase
import com.example.stageconnect.domain.usecases.workexperience.UpdateWorkExperienceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WorkExperienceViewModel @Inject constructor(
    private val createWorkExperienceUseCase: CreateWorkExperienceUseCase,
    private val storageRepository: StorageRepository,
    private val deleteWorkExperienceUseCase: DeleteWorkExperienceUseCase,
    private val getAllWorkExperienceUseCase: GetAllWorkExperienceUseCase,
    private val updateWorkExperienceUseCase: UpdateWorkExperienceUseCase
) : ViewModel() {

    private val _data = MutableLiveData<MutableList<WorkExperienceDto>>()
    val data: LiveData<MutableList<WorkExperienceDto>> get() = _data

    private val _createWorkExperienceResult = MutableLiveData<Result<WorkExperienceDto>>()
    val createWorkExperienceResult: LiveData<Result<WorkExperienceDto>> get() = _createWorkExperienceResult

    private val _deleteWorkExperienceResult = MutableLiveData<Result<Unit>>()
    val deleteWorkExperienceResult: LiveData<Result<Unit>> get() = _deleteWorkExperienceResult

    private val _updateWorkExperienceResult = MutableLiveData<Result<WorkExperienceDto>>()
    val updateWorkExperienceResult: LiveData<Result<WorkExperienceDto>> get() = _updateWorkExperienceResult

    private val _getAllWorkExperienceResult = MutableLiveData<Result<List<WorkExperienceDto>>>()
    val getAllWorkExperienceResult: LiveData<Result<List<WorkExperienceDto>>> get() = _getAllWorkExperienceResult

    private val _workExperience = mutableStateOf<WorkExperienceDto?>(null)

    private val _deleteWorkExperience = MutableLiveData<Boolean?>(false)
    val deleteWorkExperience: LiveData<Boolean?> get() = _deleteWorkExperience

    fun createWorkExperience(request: WorkExperienceDto) {
        request.userId = storageRepository.get(label = USER_ID)!!.toLong()
        viewModelScope.launch {
            _createWorkExperienceResult.postValue(Result.Loading())
            delay(100)
            try {
                val result = createWorkExperienceUseCase.execute(request)
                _createWorkExperienceResult.postValue(Result.Success(result))
                clearData()
            } catch (e: Exception) {
                _createWorkExperienceResult.postValue(Result.Error(e))
            }
        }
    }

    fun getAllWorkExperience() {
        val userId = storageRepository.get(label = USER_ID)!!.toLong()
        viewModelScope.launch {
            _getAllWorkExperienceResult.postValue(Result.Loading())
            delay(100)
            try {
                val result = getAllWorkExperienceUseCase.execute(userId)
                _data.value = result.toMutableList()
                _getAllWorkExperienceResult.postValue(Result.Success(result))
                clearData()
            } catch (e: Exception) {
                _getAllWorkExperienceResult.postValue(Result.Error(e))
            }
        }
    }

    fun deleteWorkExperience(id: Long) {
        viewModelScope.launch {
            _deleteWorkExperienceResult.postValue(Result.Loading())
            delay(100)
            try {
                deleteWorkExperienceUseCase.execute(id)
                _deleteWorkExperienceResult.postValue(Result.Success(Unit))
                clearData()
            } catch (e: Exception) {
                _deleteWorkExperienceResult.postValue(Result.Error(e))
            }
        }
    }

    fun updateWorkExperience(request: WorkExperienceDto) {
        viewModelScope.launch {
            _updateWorkExperienceResult.postValue(Result.Loading())
            delay(100)
            try {
                val result = updateWorkExperienceUseCase.execute(request.id!!, request)
                _updateWorkExperienceResult.postValue(Result.Success(result))
                clearData()
            } catch (e: Exception) {
                _updateWorkExperienceResult.postValue(Result.Error(e))
            }
        }
    }

    fun setWorkExperience(workExperienceDto: WorkExperienceDto?){
        this._workExperience.value = workExperienceDto
    }

    fun getWorkExperience(): WorkExperienceDto?{
        return this._workExperience.value
    }

    fun setDeleteWorkExperience(){
        this._deleteWorkExperience.value = true
    }

    fun clearData(){
        this._getAllWorkExperienceResult.value = null
        this._createWorkExperienceResult.value = null
        this._updateWorkExperienceResult.value = null
        this._deleteWorkExperienceResult.value = null
        this._deleteWorkExperience.value = null
    }
}
