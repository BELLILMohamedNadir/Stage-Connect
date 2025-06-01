package com.example.stageconnect.presentation.screens.signup.viewmodels

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stageconnect.data.dtos.AuthDto
import com.example.stageconnect.data.dtos.EstablishmentsDto
import com.example.stageconnect.domain.model.enums.ROLE
import com.example.stageconnect.domain.usecases.auth.RegisterUseCase
import com.example.stageconnect.domain.result.Result
import com.example.stageconnect.domain.usecases.user.GetAllEstablishmentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val establishmentUseCase: GetAllEstablishmentUseCase
) : ViewModel() {

    private val _registerResult = MutableLiveData<Result<String>?>()
    val registerResult: MutableLiveData<Result<String>?> get() = _registerResult

    private var _selectedIndex: Int = 1

    private val _getAllEstablishmentResult = MutableLiveData<Result<List<EstablishmentsDto>>?>()
    val getAllEstablishmentResult: MutableLiveData<Result<List<EstablishmentsDto>>?> get() = _getAllEstablishmentResult

    private val _role = MutableLiveData<ROLE>(ROLE.STUDENT)
    private val _email = MutableLiveData<String>("")
    private val _password = MutableLiveData<String>("")
    private val _expertises = MutableLiveData<List<String>>(listOf(""))

    fun getRole(): ROLE? = _role.value
    fun getEmail(): String? = _email.value
    fun getPassword(): String? = _password.value
    fun getExpertise(): List<String>? = _expertises.value

    fun register(authDto: AuthDto, imageUri: Uri?) {
        viewModelScope.launch {
            _registerResult.postValue(Result.Loading(""))
            delay(100)
            try {
                val result = registerUseCase.execute(authDto, imageUri)
                _registerResult.postValue(Result.Success(result))
            } catch (e: Exception) {
                _registerResult.postValue(Result.Error(e))
            }
        }
    }


    fun getAllEstablishment() {
        viewModelScope.launch {
            try {
                val result = establishmentUseCase.execute()
                _getAllEstablishmentResult.postValue(Result.Success(result))
            } catch (e: Exception) {
                _getAllEstablishmentResult.postValue(Result.Error(e))
            }
        }
    }

    fun clearData() {
        _registerResult.value = null
        _getAllEstablishmentResult.value = null
        _role.value = ROLE.STUDENT
        _email.value = ""
        _password.value = ""
        _expertises.value = listOf("")
    }

    fun resetEstablishments(){
        _getAllEstablishmentResult.value = null
    }

    fun setRole(role: ROLE){
        _role.value = role
    }
    fun setEmail(email: String){
        _email.value = email
    }
    fun setPassword(password: String){
        _password.value = password
    }
    fun setExpertises(expertise: List<String>){
        _expertises.value = expertise
    }

    fun setSelectedIndex(index: Int){
        this._selectedIndex = index
    }

    fun getSelectedIndex():Int{
        return this._selectedIndex
    }
}
