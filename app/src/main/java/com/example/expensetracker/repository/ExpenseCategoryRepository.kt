package com.example.expensetracker.repository

import androidx.lifecycle.LiveData
import com.example.expensetracker.dao.ExpenseCategoryDao
import com.example.expensetracker.model.ExpenseCategory
import kotlin.math.exp

class ExpenseCategoryRepository(private val expenseCategoryDao: ExpenseCategoryDao) {

    suspend fun insert(expenseCategory: ExpenseCategory) {
        expenseCategoryDao.insert(expenseCategory)
    }

    suspend fun delete(expenseCategory: ExpenseCategory) {
        expenseCategoryDao.delete(expenseCategory)
    }

    fun getAllExpenseCategory(): LiveData<List<ExpenseCategory>> {
        return expenseCategoryDao.getAllExpenseCategories()
    }
}