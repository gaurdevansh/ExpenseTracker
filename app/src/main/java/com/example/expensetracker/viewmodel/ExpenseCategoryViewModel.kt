package com.example.expensetracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.model.ExpenseCategory
import com.example.expensetracker.repository.ExpenseCategoryRepository
import kotlinx.coroutines.launch

class ExpenseCategoryViewModel(private val repository: ExpenseCategoryRepository): ViewModel() {

    fun insert(expenseCategory: ExpenseCategory) {
        viewModelScope.launch {
            repository.insert(expenseCategory)
        }
    }

    fun delete(expenseCategory: ExpenseCategory) {
        viewModelScope.launch {
            repository.delete(expenseCategory)
        }
    }

    fun getAllExpenseCategory(): LiveData<List<ExpenseCategory>> {
        return repository.getAllExpenseCategory()
    }
}