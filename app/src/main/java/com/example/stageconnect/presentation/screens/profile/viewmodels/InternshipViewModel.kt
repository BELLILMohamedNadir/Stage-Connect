package com.example.stageconnect.presentation.screens.profile.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stageconnect.data.dtos.EducationDto
import com.example.stageconnect.data.dtos.InternshipDto
import com.example.stageconnect.data.dtos.WorkExperienceDto
import com.example.stageconnect.data.remote.repository.StorageRepository
import com.example.stageconnect.domain.CONSTANT.USER_ID
import com.example.stageconnect.domain.result.Result
import com.example.stageconnect.domain.usecases.education.CreateEducationUseCase
import com.example.stageconnect.domain.usecases.education.DeleteEducationUseCase
import com.example.stageconnect.domain.usecases.education.GetAllEducationUseCase
import com.example.stageconnect.domain.usecases.education.UpdateEducationUseCase
import com.example.stageconnect.domain.usecases.internship.CreateInternshipUseCase
import com.example.stageconnect.domain.usecases.internship.DeleteInternshipUseCase
import com.example.stageconnect.domain.usecases.internship.GetAllInternshipUseCase
import com.example.stageconnect.domain.usecases.internship.UpdateInternshipUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class InternshipViewModel @Inject constructor(
    private val createInternshipUseCase: CreateInternshipUseCase,
    private val storageRepository: StorageRepository,
    private val deleteInternshipUseCase: DeleteInternshipUseCase,
    private val getAllInternshipUseCase: GetAllInternshipUseCase,
    private val updateInternshipUseCase: UpdateInternshipUseCase
) : ViewModel() {

    private val _data = MutableLiveData<MutableList<InternshipDto>>()
    val data: LiveData<MutableList<InternshipDto>> get() = _data

    private val _createInternshipResult = MutableLiveData<Result<InternshipDto>>()
    val createInternshipResult: LiveData<Result<InternshipDto>> get() = _createInternshipResult

    private val _deleteInternshipResult = MutableLiveData<Result<Unit>>()
    val deleteInternshipResult: LiveData<Result<Unit>> get() = _deleteInternshipResult

    private val _updateInternshipResult = MutableLiveData<Result<InternshipDto>>()
    val updateInternshipResult: LiveData<Result<InternshipDto>> get() = _updateInternshipResult

    private val _getAllInternshipResult = MutableLiveData<Result<List<InternshipDto>>>()
    val getAllInternshipResult: LiveData<Result<List<InternshipDto>>> get() = _getAllInternshipResult

    private val _internship = mutableStateOf<InternshipDto?>(value = null)

    private val _deleteInternship = MutableLiveData<Boolean?>(false)
    val deleteInternship: LiveData<Boolean?> get() = _deleteInternship

    fun createInternship(request: InternshipDto) {
        request.userId = storageRepository.get(label = USER_ID)!!.toLong()
        viewModelScope.launch {
            _createInternshipResult.postValue(Result.Loading())
            delay(100)
            try {
                val result = createInternshipUseCase.execute(request)
                _createInternshipResult.postValue(Result.Success(result))
                clearData()
            } catch (e: Exception) {
                _createInternshipResult.postValue(Result.Error(e))
            }
        }
    }

    fun getAllInternship() {
        val userId = storageRepository.get(label = USER_ID)!!.toLong()
        viewModelScope.launch {
            _getAllInternshipResult.postValue(Result.Loading())
            delay(100)
            try {
                val result = getAllInternshipUseCase.execute(userId)
                _data.value = result.toMutableList()
                _getAllInternshipResult.postValue(Result.Success(result))
                clearData()
            } catch (e: Exception) {
                _getAllInternshipResult.postValue(Result.Error(e))
            }
        }
    }

    fun deleteInternship(id: Long) {
        viewModelScope.launch {
            _deleteInternshipResult.postValue(Result.Loading())
            delay(100)
            try {
                deleteInternshipUseCase.execute(id)
                _deleteInternshipResult.postValue(Result.Success(Unit))
                clearData()
            } catch (e: Exception) {
                _deleteInternshipResult.postValue(Result.Error(e))
            }
        }
    }

    fun updateInternship(request: InternshipDto) {
        viewModelScope.launch {
            _updateInternshipResult.postValue(Result.Loading())
            delay(100)
            try {
                val result = updateInternshipUseCase.execute(request.id!!, request)
                _updateInternshipResult.postValue(Result.Success(result))
                clearData()
            } catch (e: Exception) {
                _updateInternshipResult.postValue(Result.Error(e))
            }
        }
    }

    fun setInternship(internshipDto: InternshipDto?){
        this._internship.value = internshipDto
    }

    fun getInternship(): InternshipDto?{
        return this._internship.value
    }

    fun setDeleteInternship(){
        this._deleteInternship.value = true
    }

    fun clearData(){
        this._getAllInternshipResult.value = null
        this._createInternshipResult.value = null
        this._updateInternshipResult.value = null
        this._deleteInternshipResult.value = null
        this._deleteInternship.value = null
    }

}
