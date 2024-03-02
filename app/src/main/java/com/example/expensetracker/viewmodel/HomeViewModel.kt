package com.example.expensetracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.model.Transaction
import com.example.expensetracker.repository.TransactionRepository
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Year
import java.time.YearMonth
import java.time.temporal.TemporalAdjuster
import java.time.temporal.TemporalAdjusters

class HomeViewModel(private val repository: TransactionRepository): ViewModel() {

    private var currentMonthStart: LocalDate = YearMonth.from(LocalDate.now()).atDay(1)
    private var currentWeekStart: LocalDate = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
    private var currentYearStart: LocalDate = Year.from(LocalDate.now()).atDay(1)
    var monthTotalExpense: MutableLiveData<Int> = MutableLiveData()
    var weekTotalExpense: MutableLiveData<Int> = MutableLiveData()
    var yearTotalExpense: MutableLiveData<Int> = MutableLiveData()
    var recentTransactions: MutableLiveData<List<Transaction>> = MutableLiveData()

    init {
        getTransactionForCurrentMonth()
        getTransactionForCurrentWeek()
        getTransactionForCurrentYear()
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

    private fun getTransactionForCurrentWeek() {
        viewModelScope.launch {
            repository.getTransactionByMonthStart(currentWeekStart)
                .observeForever { transactionList ->
                    weekTotalExpense.postValue(transactionList.sumOf { it.amount.toInt() })
                }
        }
    }

    private fun getTransactionForCurrentYear() {
        viewModelScope.launch {
            repository.getTransactionByMonthStart(currentYearStart)
                .observeForever { transactionList ->
                    yearTotalExpense.postValue(transactionList.sumOf { it.amount.toInt() })
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