package com.example.stageconnect.presentation.screens.profile.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stageconnect.data.dtos.ProjectDto
import com.example.stageconnect.data.remote.repository.StorageRepository
import com.example.stageconnect.domain.CONSTANT.USER_ID
import com.example.stageconnect.domain.result.Result
import com.example.stageconnect.domain.usecases.project.CreateProjectUseCase
import com.example.stageconnect.domain.usecases.project.DeleteProjectUseCase
import com.example.stageconnect.domain.usecases.project.GetAllProjectUseCase
import com.example.stageconnect.domain.usecases.project.UpdateProjectUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProjectViewModel @Inject constructor(
    private val createProjectUseCase: CreateProjectUseCase,
    private val storageRepository: StorageRepository,
    private val deleteProjectUseCase: DeleteProjectUseCase,
    private val getAllProjectUseCase: GetAllProjectUseCase,
    private val updateProjectUseCase: UpdateProjectUseCase
) : ViewModel() {

    private val _data = MutableLiveData<MutableList<ProjectDto>>()
    val data: LiveData<MutableList<ProjectDto>> get() = _data

    private val _createProjectResult = MutableLiveData<Result<ProjectDto>>()
    val createProjectResult: LiveData<Result<ProjectDto>> get() = _createProjectResult

    private val _deleteProjectResult = MutableLiveData<Result<Unit>>()
    val deleteProjectResult: LiveData<Result<Unit>> get() = _deleteProjectResult

    private val _updateProjectResult = MutableLiveData<Result<ProjectDto>>()
    val updateProjectResult: LiveData<Result<ProjectDto>> get() = _updateProjectResult

    private val _getAllProjectResult = MutableLiveData<Result<List<ProjectDto>>>()
    val getAllProjectResult: LiveData<Result<List<ProjectDto>>> get() = _getAllProjectResult

    private val _project = mutableStateOf<ProjectDto?>(value = null)

    private val _deleteProject = MutableLiveData<Boolean?>(false)
    val deleteProject: LiveData<Boolean?> get() = _deleteProject

    fun createProject(request: ProjectDto) {
        request.userId = storageRepository.get(label = USER_ID)!!.toLong()
        viewModelScope.launch {
            _createProjectResult.postValue(Result.Loading())
            delay(100)
            try {
                val result = createProjectUseCase.execute(request)
                _createProjectResult.postValue(Result.Success(result))
                clearData()
            } catch (e: Exception) {
                _createProjectResult.postValue(Result.Error(e))
            }
        }
    }

    fun getAllProject() {
        val userId = storageRepository.get(label = USER_ID)!!.toLong()
        viewModelScope.launch {
            _getAllProjectResult.postValue(Result.Loading())
            delay(100)
            try {
                val result = getAllProjectUseCase.execute(userId)
                _data.value = result.toMutableList()
                _getAllProjectResult.postValue(Result.Success(result))
                clearData()
            } catch (e: Exception) {
                _getAllProjectResult.postValue(Result.Error(e))
            }
        }
    }

    fun deleteProject(id: Long) {
        viewModelScope.launch {
            _deleteProjectResult.postValue(Result.Loading())
            delay(100)
            try {
                deleteProjectUseCase.execute(id)
                _deleteProjectResult.postValue(Result.Success(Unit))
                clearData()
            } catch (e: Exception) {
                _deleteProjectResult.postValue(Result.Error(e))
            }
        }
    }

    fun updateProject(request: ProjectDto) {
        viewModelScope.launch {
            _updateProjectResult.postValue(Result.Loading())
            delay(100)
            try {
                val result = updateProjectUseCase.execute(request.id!!, request)
                _updateProjectResult.postValue(Result.Success(result))
                clearData()
            } catch (e: Exception) {
                _updateProjectResult.postValue(Result.Error(e))
            }
        }
    }

    fun setProject(projectDto: ProjectDto?){
        this._project.value = projectDto
    }

    fun getProject(): ProjectDto?{
        return this._project.value
    }

    fun setDeleteProject(){
        this._deleteProject.value = true
    }

    fun clearData(){
        this._getAllProjectResult.value = null
        this._createProjectResult.value = null
        this._updateProjectResult.value = null
        this._deleteProjectResult.value = null
        this._deleteProject.value = null
    }
}
