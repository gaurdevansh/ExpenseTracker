package com.example.expensetracker.viewmodelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.repository.TransactionRepository
import com.example.expensetracker.viewmodel.HomeViewModel
import com.example.expensetracker.viewmodel.TransactionViewModel

class HomeViewModelFactory(private val repository: TransactionRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}