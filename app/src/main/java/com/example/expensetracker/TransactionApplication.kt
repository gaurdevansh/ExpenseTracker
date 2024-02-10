package com.example.expensetracker

import android.app.Application
import com.example.expensetracker.repository.TransactionRepository

class TransactionApplication : Application() {

    lateinit var repository: TransactionRepository
        private set

    override fun onCreate() {
        super.onCreate()
        repository = TransactionRepository(AppDatabase.getInstance(this).transactionDao())
    }
}