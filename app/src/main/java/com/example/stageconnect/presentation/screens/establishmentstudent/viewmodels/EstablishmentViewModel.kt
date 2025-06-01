package com.example.stageconnect.presentation.screens.establishmentstudent.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stageconnect.data.dtos.StudentDto
import com.example.stageconnect.data.remote.repository.StorageRepository
import com.example.stageconnect.domain.CONSTANT.USER_ID
import com.example.stageconnect.domain.result.Result
import com.example.stageconnect.domain.usecases.filter.FilterUseCase
import com.example.stageconnect.domain.usecases.user.GetAllEstablishmentStudentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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