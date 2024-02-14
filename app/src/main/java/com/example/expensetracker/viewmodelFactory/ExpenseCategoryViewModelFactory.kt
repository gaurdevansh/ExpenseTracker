package com.example.expensetracker.viewmodelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.model.ExpenseCategory
import com.example.expensetracker.repository.ExpenseCategoryRepository
import com.example.expensetracker.viewmodel.ExpenseCategoryViewModel

@Suppress("UNCHECKED_CAST")
class ExpenseCategoryViewModelFactory(private val repository: ExpenseCategoryRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpenseCategoryViewModel::class.java)) {
            return ExpenseCategoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}