package com.example.expensetracker.viewmodelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.repository.TransactionRepository
import com.example.expensetracker.viewmodel.TransactionViewModel

@Suppress("UNCHECKED_CAST")
class TransactionViewModelFactory(private val repository: TransactionRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionViewModel::class.java)) {
            return TransactionViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}