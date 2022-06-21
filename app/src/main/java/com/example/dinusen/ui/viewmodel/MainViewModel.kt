package com.example.dinusen.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dinusen.data.remote.response.HomeResponse
import com.example.dinusen.helper.Event
import com.example.dinusen.repository.RetrofitRepository
import kotlinx.coroutines.launch

class MainViewModel(private val retrofitRepository: RetrofitRepository): ViewModel() {
    private val _events = MutableLiveData<Event<HomeResponse>>()
    val events: LiveData<Event<HomeResponse>> = _events

    private val _isLoading = MutableLiveData<Event<Boolean>>()
    val isLoading: LiveData<Event<Boolean>> = _isLoading

    fun getAllEvent() {
        viewModelScope.launch {
            _isLoading.postValue(Event(true))
            val response = retrofitRepository.getAllEvent() ?: return@launch
            _events.postValue(Event(response))
            _isLoading.postValue(Event(false))
        }
    }
}