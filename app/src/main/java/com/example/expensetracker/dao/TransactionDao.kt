package com.example.expensetracker.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.expensetracker.model.Transaction

@Dao
interface TransactionDao {

    @Insert
    fun insert(transaction: Transaction)

    @Query("SELECT * FROM transaction_table")
    fun getAllTransactions(): List<Transaction>
}