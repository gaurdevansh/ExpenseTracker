package com.example.expensetracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.model.Transaction
import com.example.expensetracker.repository.TransactionRepository
import kotlinx.coroutines.launch
import java.time.LocalDate

class TransactionViewModel(private val repository: TransactionRepository): ViewModel() {

    fun insert(transaction: Transaction) {
        viewModelScope.launch {
            repository.insert(transaction)
        }
    }

    fun getTransactions(): LiveData<List<Transaction>> {
        return repository.getTransactions()
    }

    fun getTransactionByDate(startData: LocalDate, endDate: LocalDate): LiveData<List<Transaction>> {
        return repository.getTransactionByDate(startData, endDate)
    }
}