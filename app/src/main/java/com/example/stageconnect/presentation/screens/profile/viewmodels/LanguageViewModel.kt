package com.example.stageconnect.presentation.screens.profile.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stageconnect.data.dtos.InternshipDto
import com.example.stageconnect.data.dtos.LanguageDto
import com.example.stageconnect.data.dtos.WorkExperienceDto
import com.example.stageconnect.data.remote.repository.StorageRepository
import com.example.stageconnect.domain.CONSTANT.USER_ID
import com.example.stageconnect.domain.result.Result
import com.example.stageconnect.domain.usecases.internship.CreateInternshipUseCase
import com.example.stageconnect.domain.usecases.internship.DeleteInternshipUseCase
import com.example.stageconnect.domain.usecases.internship.GetAllInternshipUseCase
import com.example.stageconnect.domain.usecases.internship.UpdateInternshipUseCase
import com.example.stageconnect.domain.usecases.language.CreateLanguageUseCase
import com.example.stageconnect.domain.usecases.language.DeleteLanguageUseCase
import com.example.stageconnect.domain.usecases.language.GetAllLanguageUseCase
import com.example.stageconnect.domain.usecases.language.UpdateLanguageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val createLanguageUseCase: CreateLanguageUseCase,
    private val storageRepository: StorageRepository,
    private val deleteLanguageUseCase: DeleteLanguageUseCase,
    private val getAllLanguageUseCase: GetAllLanguageUseCase,
    private val updateLanguageUseCase: UpdateLanguageUseCase
) : ViewModel() {

    private val _data = MutableLiveData<MutableList<LanguageDto>>()
    val data: LiveData<MutableList<LanguageDto>> get() = _data

    private val _createLanguageResult = MutableLiveData<Result<LanguageDto>>()
    val createLanguageResult: LiveData<Result<LanguageDto>> get() = _createLanguageResult

    private val _deleteLanguageResult = MutableLiveData<Result<Unit>>()
    val deleteLanguageResult: LiveData<Result<Unit>> get() = _deleteLanguageResult

    private val _updateLanguageResult = MutableLiveData<Result<LanguageDto>>()
    val updateLanguageResult: LiveData<Result<LanguageDto>> get() = _updateLanguageResult

    private val _getAllLanguageResult = MutableLiveData<Result<List<LanguageDto>>>()
    val getAllLanguageResult: LiveData<Result<List<LanguageDto>>> get() = _getAllLanguageResult

    private val _language = MutableLiveData<LanguageDto>()

    private val _deleteLanguage = MutableLiveData<Boolean?>(false)
    val deleteLanguage: LiveData<Boolean?> get() = _deleteLanguage

    fun createLanguage(request: LanguageDto) {
        request.userId = storageRepository.get(label = USER_ID)!!.toLong()
        viewModelScope.launch {
            _createLanguageResult.postValue(Result.Loading())
            delay(100)
            try {
                val result = createLanguageUseCase.execute(request)
                _createLanguageResult.postValue(Result.Success(result))
                clearData()
            } catch (e: Exception) {
                _createLanguageResult.postValue(Result.Error(e))
            }
        }
    }

    fun getAllLanguage() {
        val userId = storageRepository.get(label = USER_ID)!!.toLong()
        viewModelScope.launch {
            _getAllLanguageResult.postValue(Result.Loading())
            delay(100)
            try {
                val result = getAllLanguageUseCase.execute(userId)
                _data.value = result.toMutableList()
                _getAllLanguageResult.postValue(Result.Success(result))
                clearData()
            } catch (e: Exception) {
                _getAllLanguageResult.postValue(Result.Error(e))
            }
        }
    }

    fun deleteLanguage(id: Long) {
        viewModelScope.launch {
            _deleteLanguageResult.postValue(Result.Loading())
            delay(100)
            try {
                deleteLanguageUseCase.execute(id)
                _deleteLanguageResult.postValue(Result.Success(Unit))
                clearData()
            } catch (e: Exception) {
                _deleteLanguageResult.postValue(Result.Error(e))
            }
        }
    }

    fun updateLanguage(request: LanguageDto) {
        viewModelScope.launch {
            _updateLanguageResult.postValue(Result.Loading())
            delay(100)
            try {
                val result = updateLanguageUseCase.execute(request.id!!, request)
                _updateLanguageResult.postValue(Result.Success(result))
                clearData()
            } catch (e: Exception) {
                _updateLanguageResult.postValue(Result.Error(e))
            }
        }
    }

    fun setLanguage(languageDto: LanguageDto?){
        this._language.value = languageDto
    }

    fun getLanguage(): LanguageDto?{
        return this._language.value
    }

    fun setDeleteLanguage(){
        this._deleteLanguage.value = true
    }

    fun clearData(){
        this._getAllLanguageResult.value = null
        this._createLanguageResult.value = null
        this._updateLanguageResult.value = null
        this._deleteLanguageResult.value = null
        this._deleteLanguage.value = null
    }
}
