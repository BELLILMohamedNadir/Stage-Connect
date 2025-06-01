package com.example.stageconnect.presentation.screens.establishmentstudent.viewmodels

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stageconnect.data.dtos.AuthenticationResponse
import com.example.stageconnect.data.dtos.OfferDto
import com.example.stageconnect.data.dtos.RecruiterDto
import com.example.stageconnect.data.dtos.StudentDto
import com.example.stageconnect.data.dtos.UserDto
import com.example.stageconnect.data.remote.repository.StorageRepository
import com.example.stageconnect.domain.CONSTANT.USER_ID
import com.example.stageconnect.domain.result.Result
import com.example.stageconnect.domain.usecases.DownloadFileUseCase
import com.example.stageconnect.domain.usecases.FilterUseCase
import com.example.stageconnect.domain.usecases.UploadFileUseCase
import com.example.stageconnect.domain.usecases.create.AddSkillsUseCase
import com.example.stageconnect.domain.usecases.read.GetAllEstablishmentStudentsUseCase
import com.example.stageconnect.domain.usecases.update.UpdateRecruiterUseCase
import com.example.stageconnect.domain.usecases.update.UpdateStudentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class EstablishmentViewModel @Inject constructor(
    private val storageRepository: StorageRepository,
    private val getAllEstablishmentStudentsUseCase: GetAllEstablishmentStudentsUseCase,
    private val filterUseCase: FilterUseCase
) : ViewModel() {

    private val _students = MutableLiveData<MutableList<StudentDto>>()
    val students: LiveData<MutableList<StudentDto>> get() = _students

    private val _getAllEstablishmentStudentsResult = MutableLiveData<Result<List<StudentDto>>?>()
    val getAllEstablishmentStudentsResult: LiveData<Result<List<StudentDto>>?> get() = _getAllEstablishmentStudentsResult

    private val _filteredItems = MutableLiveData<List<StudentDto>>(emptyList())
    val filteredItems: LiveData<List<StudentDto>> get() = _filteredItems

    fun getAllEstablishmentStudents(){
        val establishmentId = storageRepository.get(label = USER_ID)!!.toLong()
        viewModelScope.launch {
            _getAllEstablishmentStudentsResult.postValue(Result.Loading())
            delay(100)
            try {
                val response = getAllEstablishmentStudentsUseCase.execute(id = establishmentId)
                _getAllEstablishmentStudentsResult.postValue(Result.Success(response))
                _students.value = response.toMutableList()
                _filteredItems.value = response
                clearData()
            } catch (e: Exception) {
                _getAllEstablishmentStudentsResult.postValue(Result.Error(e))
            }
        }
    }

    fun applyFilter(searchedText: String) {
        if (searchedText.isEmpty()) {
            _filteredItems.value = _students.value
        } else {
            val filtered = filterUseCase.filterEstablishmentStudents(_students.value ?: emptyList(), searchedText)
            _filteredItems.value = filtered
        }
    }

    fun clearData() {
        _getAllEstablishmentStudentsResult.value = null
    }
}