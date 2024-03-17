package com.example.expensetracker.viewmodel

import android.provider.ContactsContract.Data
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.model.Transaction
import com.example.expensetracker.repository.TransactionRepository
import com.example.expensetracker.utils.DateUtils
import com.example.expensetracker.utils.TimeFrame
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class TransactionViewModel(private val repository: TransactionRepository) : ViewModel() {

    var transactionData: MutableLiveData<List<Transaction>> = MutableLiveData()
    var fromDate: LocalDate = LocalDate.now()
    var toDate: LocalDate = LocalDate.now()
    var allTransactionData: MutableLiveData<List<Transaction>> = MutableLiveData()

    fun insert(transaction: Transaction) {
        viewModelScope.launch {
            repository.insert(transaction)
        }
    }

    fun getTransactions(
        lifecycleOwner: LifecycleOwner
    ) {
        viewModelScope.launch {
            repository.getTransactions()
                .observe(lifecycleOwner) { transactionList ->
                    allTransactionData.postValue(transactionList)
                }
        }
    }

    fun getTransactionByDate(
        lifecycleOwner: LifecycleOwner
    ) {
        viewModelScope.launch {
            repository.getTransactionByDate(fromDate, toDate)
                .observe(lifecycleOwner) { transactionList ->
                    transactionData.postValue(transactionList)
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }

}

