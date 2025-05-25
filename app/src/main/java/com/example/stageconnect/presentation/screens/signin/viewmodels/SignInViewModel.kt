package com.example.stageconnect.presentation.screens.signin.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stageconnect.data.dtos.AuthenticationRequest
import com.example.stageconnect.data.dtos.AuthenticationResponse
import com.example.stageconnect.data.remote.repository.StorageRepository
import com.example.stageconnect.domain.CONSTANT
import com.example.stageconnect.domain.CONSTANT.JWT_TOKEN
import com.example.stageconnect.domain.CONSTANT.USER_ID
import com.example.stageconnect.domain.CONSTANT.USER_ROLE
import com.example.stageconnect.domain.result.Result
import com.example.stageconnect.domain.usecases.AuthenticateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authenticateUseCase: AuthenticateUseCase,
    private val storageRepository: StorageRepository
) : ViewModel() {

    private val _authenticationResult = MutableLiveData<Result<AuthenticationResponse>>()
    val authenticationResult: LiveData<Result<AuthenticationResponse>> get() = _authenticationResult


    fun authenticate(request: AuthenticationRequest) {
        viewModelScope.launch {
            _authenticationResult.postValue(Result.Loading())
            delay(100)
            try {
                val result = authenticateUseCase.execute(request)
                storageRepository.save(data = result.token, label = JWT_TOKEN)
                storageRepository.save(data = "${result.id}", label = USER_ID)
                storageRepository.save(data = result.role, label = USER_ROLE)

                _authenticationResult.postValue(Result.Success(result))
            } catch (e: Exception) {
                _authenticationResult.postValue(Result.Error(e))
            }
        }
    }

}
