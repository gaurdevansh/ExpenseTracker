package com.example.expensetracker.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.expensetracker.model.ExpenseCategory

@Dao
interface ExpenseCategoryDao {

    @Insert
    suspend fun insert(expenseCategory: ExpenseCategory)

    @Delete
    suspend fun delete(expenseCategory: ExpenseCategory)

    @Query("SELECT * FROM expense_category_table")
    fun getAllExpenseCategories(): LiveData<List<ExpenseCategory>>
}