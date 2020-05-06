package com.example.appiness.core.application.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appiness.domain.remoteusecase.BakersRemoteDataUseCase
import com.example.appiness.presentation.viewmodels.MainViewModel
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ViewModelProviderFactory @Inject constructor(
    private var bakersRemoteDataUseCase: BakersRemoteDataUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(bakersRemoteDataUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}