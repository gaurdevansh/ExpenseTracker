package com.example.expensetracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.model.Transaction
import com.example.expensetracker.repository.TransactionRepository
import kotlinx.coroutines.launch
import java.time.LocalDate

class TransactionViewModel(private val repository: TransactionRepository): ViewModel() {

    val data: MutableLiveData<List<Transaction>> = MutableLiveData()
    /*val data: LiveData<List<Transaction>>
        get() = _data*/
    lateinit var fromDate: LocalDate
    lateinit var toDate: LocalDate

    init {
        fromDate = LocalDate.now()
        toDate = LocalDate.now()
    }

    fun insert(transaction: Transaction) {
        viewModelScope.launch {
            repository.insert(transaction)
        }
    }

    fun getTransactions(): LiveData<List<Transaction>> {
        return repository.getTransactions()
    }

    fun getTransactionByDate(startData: LocalDate, endDate: LocalDate) {
        //_data.postValue(repository.getTransactionByDate(startData, endDate))
        repository.getTransactionByDate(startData, endDate).observeForever { newData ->
            data.postValue(newData)
        }
    }
}