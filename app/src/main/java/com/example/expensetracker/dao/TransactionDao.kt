package com.example.expensetracker.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.expensetracker.model.Transaction
import java.time.LocalDate

@Dao
interface TransactionDao {

    @Insert
    suspend fun insert(transaction: Transaction)

    @Delete
    suspend fun delete(transaction: Transaction): Int

    @Query("SELECT * FROM transaction_table")
    fun getTransactions(): LiveData<List<Transaction>>

    @Query("SELECT * FROM transaction_table where date >= :startDate")
    fun getTransactionByDate(startDate: LocalDate): LiveData<List<Transaction>>
}