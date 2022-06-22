package com.example.dinusen.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dinusen.repository.RetrofitRepository

class RetrofitViewModelFactory private constructor(private val retrofitRepository: RetrofitRepository) : ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(retrofitRepository) as T
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> DetailViewModel(retrofitRepository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel Class : ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: RetrofitViewModelFactory? = null

        @JvmStatic
        fun getInstance(retrofitRepository: RetrofitRepository): RetrofitViewModelFactory {
            if (INSTANCE == null) {
                synchronized(RetrofitViewModelFactory::class.java) {
                    INSTANCE = RetrofitViewModelFactory(retrofitRepository)
                }
            }
            return INSTANCE as RetrofitViewModelFactory
        }
    }

}