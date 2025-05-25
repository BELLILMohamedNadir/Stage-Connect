package com.example.stageconnect.presentation.screens.messaging.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stageconnect.data.dtos.MessageDto
import com.example.stageconnect.data.dtos.RoomDto
import com.example.stageconnect.data.remote.repository.StorageRepository
import com.example.stageconnect.domain.CONSTANT.USER_ID
import com.example.stageconnect.domain.CONSTANT.USER_ROLE
import com.example.stageconnect.domain.result.Result
import com.example.stageconnect.domain.usecases.FilterUseCase
import com.example.stageconnect.domain.usecases.read.GetAllMessagesUseCase
import com.example.stageconnect.domain.usecases.read.GetAllRoomsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(
    private val storageRepository: StorageRepository,
    private val getAllRoomsUseCase: GetAllRoomsUseCase,
    private val getAllMessages: GetAllMessagesUseCase,
    private val filterUseCase: FilterUseCase
) : ViewModel()  {
    private val _rooms = MutableLiveData<MutableList<RoomDto>>()
    val rooms: LiveData<MutableList<RoomDto>> get() = _rooms

    private val _filteredRooms = MutableLiveData<List<RoomDto>>()
    val filteredRooms: LiveData<List<RoomDto>> get() = _filteredRooms

    private val _messages = MutableLiveData<MutableList<MessageDto>>()
    val messages: LiveData<MutableList<MessageDto>> get() = _messages

    private val _getAllRoomsResult = MutableLiveData<Result<List<RoomDto?>>?>()
    val getAllRoomsResult: MutableLiveData<Result<List<RoomDto?>>?> get() = _getAllRoomsResult

    private val _getAllMessagesResult = MutableLiveData<Result<List<MessageDto?>>?>()
    val getAllMessagesResult: LiveData<Result<List<MessageDto?>>?> get() = _getAllMessagesResult

    private val _room = MutableLiveData<RoomDto>()
    val room: LiveData<RoomDto> get() = _room

    fun applyFilter(searchedText: String) {
        if(searchedText.isEmpty()){
            _filteredRooms.value = _rooms.value
        }else{
            val filtered = filterUseCase.filterRooms(_rooms.value ?: emptyList(), searchedText)
            _filteredRooms.value = filtered
        }
    }

    fun getAllRooms() {
        val studentId = storageRepository.get(USER_ID)!!.toLong()
        viewModelScope.launch {
            _getAllRoomsResult.postValue(Result.Loading())
            delay(300)
            try {
                val result = getAllRoomsUseCase.execute(studentId)
                _rooms.value = result.toMutableList()
                _getAllRoomsResult.postValue(Result.Success(result))
                _filteredRooms.value = _rooms.value
//                clearDate()
            } catch (e: Exception) {
                _getAllRoomsResult.postValue(Result.Error(e))
            }
        }
    }

    fun getAllMessages() {
        viewModelScope.launch {
            _getAllMessagesResult.postValue(Result.Loading())
            delay(300)
            try {
                val result = getAllMessages.execute(room.value!!.id)
                _messages.value = result.toMutableList()
                _getAllMessagesResult.postValue(Result.Success(result))
//                clearDate()
            } catch (e: Exception) {
                _getAllMessagesResult.postValue(Result.Error(e))
            }
        }
    }

    fun getUserId(): Long = storageRepository.get(USER_ID)!!.toLong()



    fun setRoom(roomDto: RoomDto){
        _room.value = roomDto
    }

    fun clearDate(){
        _getAllRoomsResult.value = null
        _getAllMessagesResult.value = null
    }
}