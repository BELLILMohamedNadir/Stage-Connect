package com.example.stageconnect.presentation.screens.profile.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stageconnect.data.dtos.ProjectDto
import com.example.stageconnect.data.dtos.WorkExperienceDto
import com.example.stageconnect.data.remote.repository.StorageRepository
import com.example.stageconnect.domain.CONSTANT.USER_ID
import com.example.stageconnect.domain.result.Result
import com.example.stageconnect.domain.usecases.create.CreateWorkExperienceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WorkExperienceViewModel @Inject constructor(
    private val workExperienceUseCase: CreateWorkExperienceUseCase,
    private val storageRepository: StorageRepository,
) : ViewModel() {

    private val _createWorkExperienceResult = MutableLiveData<Result<WorkExperienceDto>>()
    val createWorkExperienceResult: LiveData<Result<WorkExperienceDto>> get() = _createWorkExperienceResult


    fun createWorkExperience(request: WorkExperienceDto) {
        request.userId = storageRepository.get(label = USER_ID)!!.toLong()
        viewModelScope.launch {
            _createWorkExperienceResult.postValue(Result.Loading())
            delay(100)
            try {
                val result = workExperienceUseCase.execute(request)
                _createWorkExperienceResult.postValue(Result.Success(result))
            } catch (e: Exception) {
                _createWorkExperienceResult.postValue(Result.Error(e))
            }
        }
    }

}
