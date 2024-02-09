package com.example.expensetracker.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensetracker.R
import com.example.expensetracker.TransactionAdapter
import com.example.expensetracker.databinding.FragmentTransactionBinding
import com.example.expensetracker.model.Transaction
import java.time.LocalDateTime


class TransactionFragment : Fragment() {

    private lateinit var binding: FragmentTransactionBinding
    private var transactionList: ArrayList<Transaction> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransactionBinding.inflate(inflater, container, false)
        //return inflater.inflate(R.layout.fragment_transaction, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpTransactionData()
        binding.transactionRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.transactionRecyclerview.adapter = TransactionAdapter(transactionList)
    }

    private fun setUpTransactionData() {
        transactionList.add(Transaction(title = "Netflix", category = "Entertainment", amount = 199, dateTime = LocalDateTime.parse("2024-02-09T15:30:00")))
        transactionList.add(Transaction(title = "Dinner", category = "Food", amount = 120, dateTime = LocalDateTime.parse("2024-02-09T15:30:00")))
        transactionList.add(Transaction(title = "Tea", category = "Food", amount = 10, dateTime = LocalDateTime.parse("2024-02-09T15:30:00")))
        transactionList.add(Transaction(title = "Lunch", category = "Food", amount = 70, dateTime = LocalDateTime.parse("2024-02-09T15:30:00")))
        transactionList.add(Transaction(title = "Netflix", category = "Entertainment", amount = 199, dateTime = LocalDateTime.parse("2024-02-09T15:30:00")))
        transactionList.add(Transaction(title = "Dinner", category = "Food", amount = 120, dateTime = LocalDateTime.parse("2024-02-09T15:30:00")))
        transactionList.add(Transaction(title = "Tea", category = "Food", amount = 10, dateTime = LocalDateTime.parse("2024-02-09T15:30:00")))
        transactionList.add(Transaction(title = "Lunch", category = "Food", amount = 70, dateTime = LocalDateTime.parse("2024-02-09T15:30:00")))
        transactionList.add(Transaction(title = "Netflix", category = "Entertainment", amount = 199, dateTime = LocalDateTime.parse("2024-02-09T15:30:00")))
        transactionList.add(Transaction(title = "Dinner", category = "Food", amount = 120, dateTime = LocalDateTime.parse("2024-02-09T15:30:00")))
        transactionList.add(Transaction(title = "Tea", category = "Food", amount = 10, dateTime = LocalDateTime.parse("2024-02-09T15:30:00")))
        transactionList.add(Transaction(title = "Lunch", category = "Food", amount = 70, dateTime = LocalDateTime.parse("2024-02-09T15:30:00")))
    }

}