package com.example.stageconnect.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.stageconnect.data.remote.repository.StorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

//TODO USE USE CASES
@HiltViewModel
class MainViewModel @Inject constructor(
    private val storageRepository: StorageRepository
) : ViewModel()  {
    fun save(data: String, label: String) {
        storageRepository.save(data = data, label = label)
    }

    fun get(label: String): String? {
       return storageRepository.get(label = label)
    }
}
