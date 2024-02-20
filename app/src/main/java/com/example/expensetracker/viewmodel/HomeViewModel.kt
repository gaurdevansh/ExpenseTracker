package com.example.expensetracker.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.expensetracker.repository.TransactionRepository
import java.time.LocalDate
import java.time.YearMonth

class HomeViewModel(private val repository: TransactionRepository): ViewModel() {

    private var currentMonthStart: LocalDate = YearMonth.from(LocalDate.now()).atDay(1)
    private lateinit var monthTotalExpense: MutableLiveData<Int>

    fun getTransactionForCurrentMonth() {
        repository.getTransactionByMonthStart(currentMonthStart).observeForever { transactionList ->
            monthTotalExpense.postValue(transactionList.sumOf { it.amount.toInt() })
        }
    }
}