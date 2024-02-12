package com.example.expensetracker.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.AppDatabase
import com.example.expensetracker.dao.TransactionDao
import com.example.expensetracker.model.Transaction
import com.example.expensetracker.repository.TransactionRepository
import kotlinx.coroutines.launch

class TransactionViewModel(private val repository: TransactionRepository): ViewModel() {

    fun insert(transaction: Transaction) {
        viewModelScope.launch {
            repository.insert(transaction)
        }
    }

    fun getTransactions(): LiveData<List<Transaction>> {
        return repository.getTransactions()
    }
}