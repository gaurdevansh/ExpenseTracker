package com.example.expensetracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.model.Transaction
import com.example.expensetracker.repository.TransactionRepository
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth

class HomeViewModel(private val repository: TransactionRepository): ViewModel() {

    private var currentMonthStart: LocalDate = YearMonth.from(LocalDate.now()).atDay(1)
    var monthTotalExpense: MutableLiveData<Int> = MutableLiveData()
    var recentTransactions: MutableLiveData<List<Transaction>> = MutableLiveData()

    init {
        getTransactionForCurrentMonth()
        getRecentTransactions()
    }

    private fun getTransactionForCurrentMonth() {
        viewModelScope.launch {
            repository.getTransactionByMonthStart(currentMonthStart)
                .observeForever { transactionList ->
                    monthTotalExpense.postValue(transactionList.sumOf { it.amount.toInt() })
                }
        }
    }

    private fun getRecentTransactions() {
        viewModelScope.launch {
            repository.getRecentTransactions(5).observeForever { transactionList ->
                recentTransactions.postValue(transactionList)
            }
        }
    }
}