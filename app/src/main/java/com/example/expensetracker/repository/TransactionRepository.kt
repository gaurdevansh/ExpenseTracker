package com.example.expensetracker.repository

import androidx.lifecycle.LiveData
import com.example.expensetracker.dao.TransactionDao
import com.example.expensetracker.model.Transaction
import java.time.LocalDate

class TransactionRepository(private val transactionDao: TransactionDao) {

    suspend fun insert(transaction: Transaction) {
        transactionDao.insert(transaction)
    }

    fun getTransactions(): LiveData<List<Transaction>> {
        return transactionDao.getTransactions()
    }

    fun getTransactionByDate(startDate: LocalDate, endDate: LocalDate): LiveData<List<Transaction>> {
        return transactionDao.getTransactionByDate(startDate, endDate)
    }
}