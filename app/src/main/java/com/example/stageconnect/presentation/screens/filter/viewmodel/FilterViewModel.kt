package com.example.stageconnect.presentation.screens.filter.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor() : ViewModel() {
    private val _criteria = mutableStateOf<Map<String, List<String>>>(emptyMap())
    val criteria: State<Map<String, List<String>>> get() = _criteria

    fun setCriteria(criteria: Map<String, List<String>>) {
        _criteria.value = criteria
    }

}
