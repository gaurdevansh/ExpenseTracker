package com.example.expensetracker.repository

import com.example.expensetracker.dao.TransactionDao
import com.example.expensetracker.model.Transaction

class TransactionRepository(private val transactionDao: TransactionDao) {

    suspend fun insert(transaction: Transaction) {
        transactionDao.insert(transaction)
    }
}