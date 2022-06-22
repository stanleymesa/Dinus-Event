package com.example.dinusen.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dinusen.data.remote.response.EventResponse
import com.example.dinusen.helper.Event
import com.example.dinusen.repository.RetrofitRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val retrofitRepository: RetrofitRepository): ViewModel() {

    private val _event = MutableLiveData<Event<EventResponse>>()
    val event: LiveData<Event<EventResponse>> = _event

    private val _isLoading = MutableLiveData<Event<Boolean>>()
    val isLoading: LiveData<Event<Boolean>> = _isLoading

    fun getEventById(id: Int) {
        viewModelScope.launch {
            _isLoading.postValue(Event(true))
            val response = retrofitRepository.getEventById(id) ?: return@launch
            _event.postValue(Event(response))
            _isLoading.postValue(Event(false))
        }
    }

}