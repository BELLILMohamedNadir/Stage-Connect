package com.example.stageconnect.presentation.screens.profile.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stageconnect.data.dtos.LanguageDto
import com.example.stageconnect.data.dtos.ProjectDto
import com.example.stageconnect.data.remote.repository.StorageRepository
import com.example.stageconnect.domain.CONSTANT.USER_ID
import com.example.stageconnect.domain.result.Result
import com.example.stageconnect.domain.usecases.create.CreateProjectUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProjectViewModel @Inject constructor(
    private val projectUseCase: CreateProjectUseCase,
    private val storageRepository: StorageRepository,
) : ViewModel() {

    private val _createProjectResult = MutableLiveData<Result<ProjectDto>>()
    val createProjectResult: LiveData<Result<ProjectDto>> get() = _createProjectResult


    fun createProject(request: ProjectDto) {
        request.userId = storageRepository.get(label = USER_ID)!!.toLong()
        viewModelScope.launch {
            _createProjectResult.postValue(Result.Loading())
            delay(100)
            try {
                val result = projectUseCase.execute(request)
                _createProjectResult.postValue(Result.Success(result))
            } catch (e: Exception) {
                _createProjectResult.postValue(Result.Error(e))
            }
        }
    }

}
