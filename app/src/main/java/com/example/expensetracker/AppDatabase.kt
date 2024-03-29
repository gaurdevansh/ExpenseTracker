package com.example.expensetracker

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.expensetracker.dao.ExpenseCategoryDao
import com.example.expensetracker.dao.TransactionDao
import com.example.expensetracker.model.ExpenseCategory
import com.example.expensetracker.model.Transaction
import com.example.expensetracker.utils.Converters

@Database(entities = [Transaction::class, ExpenseCategory::class], version = 3)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun transactionDao(): TransactionDao
    abstract fun expenseCategoryDao(): ExpenseCategoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "expense_tracker_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}