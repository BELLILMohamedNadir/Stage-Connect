package com.example.stageconnect.presentation.screens.applications.viewmodels

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stageconnect.data.dtos.ApplicationDto
import com.example.stageconnect.data.dtos.OfferDto
import com.example.stageconnect.data.remote.repository.StorageRepository
import com.example.stageconnect.domain.CONSTANT.JWT_TOKEN
import com.example.stageconnect.domain.CONSTANT.USER_ID
import com.example.stageconnect.domain.CONSTANT.USER_ROLE
import com.example.stageconnect.domain.model.Application
import com.example.stageconnect.domain.model.enums.STATUS
import com.example.stageconnect.domain.result.Result
import com.example.stageconnect.domain.usecases.FilterUseCase
import com.example.stageconnect.domain.usecases.create.CreateApplicationUseCase
import com.example.stageconnect.domain.usecases.delete.DeleteApplicationUseCase
import com.example.stageconnect.domain.usecases.read.GetEstablishmentApplicationsUseCase
import com.example.stageconnect.domain.usecases.read.GetRecruiterApplicationsUseCase
import com.example.stageconnect.domain.usecases.read.GetStudentApplicationsUseCase
import com.example.stageconnect.domain.usecases.update.UpdateApplicationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okio.Path.Companion.toPath
import javax.inject.Inject

@HiltViewModel
class ApplicationViewModel @Inject constructor(
    private var filterUseCase: FilterUseCase,
    private var storageRepository: StorageRepository,
    private val createApplicationUseCase: CreateApplicationUseCase,
    private val getStudentApplicationsUseCase: GetStudentApplicationsUseCase,
    private val getRecruiterApplicationsUseCase: GetRecruiterApplicationsUseCase,
    private val getEstablishmentApplicationsUseCase: GetEstablishmentApplicationsUseCase,
    private val deleteApplicationUseCase: DeleteApplicationUseCase,
    private val updateApplicationUseCase: UpdateApplicationUseCase
) : ViewModel()  {

    private val _applications = MutableLiveData<List<ApplicationDto>>()
    val applications = _applications

    private val _filteredApplications = MutableLiveData<List<ApplicationDto>>()
    val filteredApplications: LiveData<List<ApplicationDto>> get() = _filteredApplications
    
    private val _application = mutableStateOf<ApplicationDto?>(null)
    val application: State<ApplicationDto?> = _application

    private val _createApplication = MutableLiveData<Result<ApplicationDto>?>()
    val createApplication: LiveData<Result<ApplicationDto>?> get() = _createApplication

    private val _getStudentApplicationResult = MutableLiveData<Result<List<ApplicationDto>>?>()
    val getStudentApplicationResult: LiveData<Result<List<ApplicationDto>>?> get() = _getStudentApplicationResult

    private val _getRecruiterApplicationResult = MutableLiveData<Result<List<ApplicationDto>>?>()
    val getRecruiterApplicationResult: LiveData<Result<List<ApplicationDto>>?> get() = _getRecruiterApplicationResult

    private val _getEstablishmentApplicationResult = MutableLiveData<Result<List<ApplicationDto>>?>()
    val getEstablishmentApplicationResult: LiveData<Result<List<ApplicationDto>>?> get() = _getEstablishmentApplicationResult

    private val _deleteApplicationResult = MutableLiveData<Result<Boolean>?>()
    val deleteApplicationResult: LiveData<Result<Boolean>?> get() = _deleteApplicationResult

    private val _updateApplicationResult = MutableLiveData<Result<Boolean>?>()
    val updateApplicationResult: LiveData<Result<Boolean>?> get() = _updateApplicationResult

    fun applyFilter(searchedText: String) {
        if(searchedText.isEmpty()){
            _filteredApplications.value = _applications.value
        }else{
            val filtered = filterUseCase.filterApplications(_applications.value ?: emptyList(), searchedText)
            _filteredApplications.value = filtered
        }
    }

    fun setApplication(newApplication: ApplicationDto){
        _application.value = newApplication
    }

    fun createApplication(request: ApplicationDto, file: Uri){
        request.studentId = storageRepository.get(USER_ID)!!.toLong()
        viewModelScope.launch {
            _createApplication.postValue(Result.Loading())
            delay(100)
            try {
                val result = createApplicationUseCase.execute(request, file)
                _createApplication.postValue(Result.Success(result))
                clearData()
            } catch (e: Exception) {
                _createApplication.postValue(Result.Error(e))
            }
        }
    }
    
    fun getStudentApplication(){
        val studentId = storageRepository.get(USER_ID)!!.toLong()
        viewModelScope.launch {
            _getStudentApplicationResult.postValue(Result.Loading())
            delay(300)
            try {
                val result = getStudentApplicationsUseCase.execute(studentId)
                _applications.value = result
                _filteredApplications.value = result
                _getStudentApplicationResult.postValue(Result.Success(result))
                clearData()
            } catch (e: Exception) {
                _getStudentApplicationResult.postValue(Result.Error(e))
            }
        }
    }

    fun getRecruiterApplication(){
        val recruiterId = storageRepository.get(USER_ID)!!.toLong()
        viewModelScope.launch {
            _getRecruiterApplicationResult.postValue(Result.Loading())
            delay(300)
            try {
                val result = getRecruiterApplicationsUseCase.execute(recruiterId)
                _applications.value = result
                _filteredApplications.value = result
                _getRecruiterApplicationResult.postValue(Result.Success(result))
                clearData()
            } catch (e: Exception) {
                _getRecruiterApplicationResult.postValue(Result.Error(e))
            }
        }
    }

    fun getEstablishmentApplication(){
        val establishmentId = storageRepository.get(USER_ID)!!.toLong()
        viewModelScope.launch {
            _getEstablishmentApplicationResult.postValue(Result.Loading())
            delay(300)
            try {
                val result = getEstablishmentApplicationsUseCase.execute(establishmentId)
                _applications.value = result
                _filteredApplications.value = result
                _getEstablishmentApplicationResult.postValue(Result.Success(result))
                clearData()
            } catch (e: Exception) {
                _getEstablishmentApplicationResult.postValue(Result.Error(e))
            }
        }
    }

    fun deleteApplication(applicationId: Long){
        val studentId = storageRepository.get(USER_ID)!!.toLong()
        viewModelScope.launch {
            _deleteApplicationResult.postValue(Result.Loading())
            delay(300)
            try {
                deleteApplicationUseCase.execute(applicationId, studentId)
                _deleteApplicationResult.postValue(Result.Success(true))
                clearData()
            } catch (e: Exception) {
                _deleteApplicationResult.postValue(Result.Error(e))
            }
        }
    }

    fun updateApplication(applicationId: Long, applicationDto: ApplicationDto){
        viewModelScope.launch {
            _updateApplicationResult.postValue(Result.Loading())
            delay(300)
            try {
                updateApplicationUseCase.execute(applicationId, applicationDto)
                _updateApplicationResult.postValue(Result.Success(true))
                clearData()
            } catch (e: Exception) {
                _updateApplicationResult.postValue(Result.Error(e))
            }
        }
    }
    
    fun clearData(){
        _createApplication.value = null
        _updateApplicationResult.value = null
        _getEstablishmentApplicationResult.value = null
        _getStudentApplicationResult.value = null
        _deleteApplicationResult.value = null
    }
}
