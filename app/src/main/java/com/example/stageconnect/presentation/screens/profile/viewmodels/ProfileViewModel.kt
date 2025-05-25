package com.example.stageconnect.presentation.screens.profile.viewmodels

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stageconnect.data.dtos.AuthenticationResponse
import com.example.stageconnect.data.dtos.RecruiterDto
import com.example.stageconnect.data.dtos.StudentDto
import com.example.stageconnect.data.dtos.UserDto
import com.example.stageconnect.data.remote.repository.StorageRepository
import com.example.stageconnect.domain.CONSTANT.USER_ID
import com.example.stageconnect.domain.result.Result
import com.example.stageconnect.domain.usecases.DownloadFileUseCase
import com.example.stageconnect.domain.usecases.UploadFileUseCase
import com.example.stageconnect.domain.usecases.create.AddSkillsUseCase
import com.example.stageconnect.domain.usecases.update.UpdateRecruiterUseCase
import com.example.stageconnect.domain.usecases.update.UpdateStudentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val updateStudentUseCase: UpdateStudentUseCase,
    private val updateRecruiterUseCase: UpdateRecruiterUseCase,
    private val addSkillsUseCase: AddSkillsUseCase,
    private val uploadFileUseCase: UploadFileUseCase,
    private val downloadFileUseCase: DownloadFileUseCase,
    private val storageRepository: StorageRepository,
) : ViewModel() {
    private val _user = mutableStateOf<UserDto?>(value = null)
    val user: State<UserDto?> = _user

    private val _fileUri = mutableStateOf<Uri?>(null)
    val fileUri: State<Uri?> = _fileUri

    private val _imageUri = mutableStateOf<Uri?>(null)
    val imageUri: State<Uri?> = _imageUri

    private val _updateStudentResult = MutableLiveData<Result<StudentDto>?>()
    val updateStudentResult: LiveData<Result<StudentDto>?> get() = _updateStudentResult

    private val _updateRecruiterResult = MutableLiveData<Result<RecruiterDto>?>()
    val updateRecruiterResult: LiveData<Result<RecruiterDto>?> get() = _updateRecruiterResult

    private val _addSkillsResult = MutableLiveData<Result<List<String>>?>()
    val addSkillsResult: LiveData<Result<List<String>>?> get() = _addSkillsResult

    private val _uploadFileResult = MutableLiveData<Result<String>?>()
    val uploadFileResult: LiveData<Result<String>?> get() = _uploadFileResult

    private val _downloadFileResult = MutableLiveData<Result<Response<ResponseBody>>?>()
    val downloadFileResult: LiveData<Result<Response<ResponseBody>>?> get() = _downloadFileResult


    fun setStudent(response: AuthenticationResponse){
        _user.value = UserDto(
            name = response.name,
            firstName = response.firstName,
            dateOfBirth = response.dateOfBirth,
            email = response.email,
            phone = response.phone,
            photo = response.photo,
            resume = response.resume,
            gender = response.gender,
            address = response.address,
            currentPosition = response.currentPosition,
        )
    }

    fun setRecruiter(response: AuthenticationResponse){
        _user.value = UserDto(
            name = response.name,
            firstName = response.firstName,
            dateOfBirth = response.dateOfBirth,
            email = response.email,
            phone = response.phone,
            photo = response.photo,
            gender = response.gender,
            address = response.address,
            currentPosition = response.currentPosition,
        )
    }

    fun onImageSelected(uri: Uri) {
        _imageUri.value = uri
    }

    fun onFileSelected(uri: Uri) {
        _fileUri.value = uri
    }

    fun updateStudent(request: StudentDto, fileUri: Uri? = null){
        request.id = storageRepository.get(label = USER_ID)!!.toLong()
        viewModelScope.launch {
            _updateStudentResult.postValue(Result.Loading())
            delay(100)
            try {
                val response = updateStudentUseCase.execute(request = request, fileUri = fileUri)
                _updateStudentResult.postValue(Result.Success(response))
                _user.value = UserDto(
                    name = response.name,
                    firstName = response.firstName,
                    dateOfBirth = response.dateOfBirth,
                    email = response.email,
                    phone = response.phone,
                    photo = response.photo,
                    resume = response.resume,
                    gender = response.gender,
                    address = response.address,
                    currentPosition = response.currentPosition,
                )
            } catch (e: Exception) {
                _updateStudentResult.postValue(Result.Error(e))
            }
        }
    }

    fun updateRecruiter(request: RecruiterDto, fileUri: Uri? = null){
        request.id = storageRepository.get(label = USER_ID)!!.toLong()
        viewModelScope.launch {
            _updateRecruiterResult.postValue(Result.Loading())
            delay(100)
            try {
                val response = updateRecruiterUseCase.execute(request = request, fileUri = fileUri)
                _updateRecruiterResult.postValue(Result.Success(response))
                _user.value = UserDto(
                    name = response.name,
                    dateOfBirth = response.dateOfBirth,
                    email = response.email,
                    phone = response.phone,
                    photo = response.photo,
                    address = response.address,
                    currentPosition = response.currentPosition,
                    organizationName = response.organizationName,
                )
            } catch (e: Exception) {
                _updateRecruiterResult.postValue(Result.Error(e))
            }
        }
    }

    fun addSkills(request: List<String>){
        viewModelScope.launch {
            _addSkillsResult.postValue(Result.Loading())
            delay(100)
            try {
                val result = addSkillsUseCase.execute(id = storageRepository.get(label = USER_ID)!!.toLong(),request)
                _addSkillsResult.postValue(Result.Success(result))
            } catch (e: Exception) {
                _addSkillsResult.postValue(Result.Error(e))
            }
        }
    }

    fun uploadFile(fileUri: Uri?, uploadPhoto: Boolean = false) {
        viewModelScope.launch {
            _uploadFileResult.postValue(Result.Loading(""))
            delay(100)
            try {
                val result = uploadFileUseCase.execute(storageRepository.get(USER_ID)!!.toLong(), fileUri, uploadPhoto)
                _uploadFileResult.postValue(Result.Success(result))
                if (result.endsWith(".pdf"))
                    _user.value?.resume = result.substringAfterLast("/")

            } catch (e: Exception) {
                _uploadFileResult.postValue(Result.Error(e))
            }
        }
    }

    fun downloadFile(fileName: String) {
        viewModelScope.launch {
            _downloadFileResult.postValue(Result.Loading(null))
            delay(100)
            try {
                val result = downloadFileUseCase.execute(fileName)
                _downloadFileResult.postValue(Result.Success(result))
            } catch (e: Exception) {
                _downloadFileResult.postValue(Result.Error(e))
            }
        }
    }

    fun clearData() {
        _updateStudentResult.value = null
        _updateRecruiterResult.value = null
        _downloadFileResult.value = null
        _uploadFileResult.value = null
        _addSkillsResult.value = null
    }
}