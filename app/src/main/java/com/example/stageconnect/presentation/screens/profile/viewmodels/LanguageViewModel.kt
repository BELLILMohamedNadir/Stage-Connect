package com.example.stageconnect.presentation.screens.profile.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stageconnect.data.dtos.LanguageDto
import com.example.stageconnect.data.remote.repository.StorageRepository
import com.example.stageconnect.domain.CONSTANT.USER_ID
import com.example.stageconnect.domain.result.Result
import com.example.stageconnect.domain.usecases.create.CreateLanguageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val languageUseCase: CreateLanguageUseCase,
    private val storageRepository: StorageRepository,
) : ViewModel() {

    private val _createLanguageResult = MutableLiveData<Result<LanguageDto>>()
    val createLanguageResult: LiveData<Result<LanguageDto>> get() = _createLanguageResult


    fun createLanguage(request: LanguageDto) {
        request.userId = storageRepository.get(label = USER_ID)!!.toLong()
        viewModelScope.launch {
            _createLanguageResult.postValue(Result.Loading())
            delay(100)
            try {
                val result = languageUseCase.execute(request)
                _createLanguageResult.postValue(Result.Success(result))
            } catch (e: Exception) {
                _createLanguageResult.postValue(Result.Error(e))
            }
        }
    }

}
