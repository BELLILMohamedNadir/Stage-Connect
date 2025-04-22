package com.example.stageconnect.presentation.screens.applications.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stageconnect.data.dummy.DummyDataProvider
import com.example.stageconnect.data.model.Application
import com.example.stageconnect.data.model.Offer
import com.example.stageconnect.domain.usecases.FilterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ApplicationViewModel @Inject constructor(
    private var filterUseCase: FilterUseCase
) : ViewModel()  {
    private val _applications = MutableLiveData<List<Application>>(DummyDataProvider.applications)
    val applications: LiveData<List<Application>> get() = _applications

    private val _filteredApplications = MutableLiveData<List<Application>>(DummyDataProvider.applications)
    val filteredApplications: LiveData<List<Application>> get() = _filteredApplications

    private val _application = mutableStateOf<Application?>(null)
    val application: State<Application?> = _application


    fun applyFilter(searchedText: String) {
        if(searchedText.isEmpty()){
            _filteredApplications.value = _applications.value
        }else{
            val filtered = filterUseCase.filterApplications(_applications.value ?: emptyList(), searchedText)
            _filteredApplications.value = filtered
        }
    }

    fun setApplication(newApplication: Application){
        _application.value = newApplication
    }

}
