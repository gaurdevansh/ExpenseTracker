package com.example.expensetracker.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensetracker.R
import com.example.expensetracker.TransactionAdapter
import com.example.expensetracker.TransactionApplication
import com.example.expensetracker.databinding.FragmentTransactionBinding
import com.example.expensetracker.model.Transaction
import com.example.expensetracker.viewmodel.TransactionViewModel
import com.example.expensetracker.viewmodelFactory.TransactionViewModelFactory
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


class TransactionFragment : Fragment() {

    private lateinit var binding: FragmentTransactionBinding
    private var transactionList: ArrayList<Transaction> = ArrayList()
    private val viewModel: TransactionViewModel by viewModels {
        TransactionViewModelFactory((requireActivity().application as TransactionApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpTransactionData()
        binding.transactionRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.transactionRecyclerview.adapter = TransactionAdapter(transactionList)
    }

    private fun setUpTransactionData() {
        transactionList.add(Transaction(title = "Netflix", category = "Entertainment", amount = 199, date = LocalDate.of(2024, 2, 12), time = LocalTime.now()))
        transactionList.add(Transaction(title = "Dinner", category = "Food", amount = 120, date = LocalDate.of(2024, 2, 12), time = LocalTime.now()))
        transactionList.add(Transaction(title = "Tea", category = "Food", amount = 10, date = LocalDate.of(2024, 2, 12), time = LocalTime.now()))
        transactionList.add(Transaction(title = "Lunch", category = "Food", amount = 70, date = LocalDate.of(2024, 2, 12), time = LocalTime.now()))
        transactionList.add(Transaction(title = "Netflix", category = "Entertainment", amount = 199, date = LocalDate.of(2024, 2, 12), time = LocalTime.now()))
        transactionList.add(Transaction(title = "Dinner", category = "Food", amount = 120, date = LocalDate.of(2024, 2, 12), time = LocalTime.now()))
        transactionList.add(Transaction(title = "Tea", category = "Food", amount = 10, date = LocalDate.of(2024, 2, 12), time = LocalTime.now()))
        transactionList.add(Transaction(title = "Lunch", category = "Food", amount = 70, date = LocalDate.of(2024, 2, 12), time = LocalTime.now()))
        transactionList.add(Transaction(title = "Netflix", category = "Entertainment", amount = 199, date = LocalDate.of(2024, 2, 12), time = LocalTime.now()))
        transactionList.add(Transaction(title = "Dinner", category = "Food", amount = 120, date = LocalDate.of(2024, 2, 12), time = LocalTime.now()))
        transactionList.add(Transaction(title = "Tea", category = "Food", amount = 10, date = LocalDate.of(2024, 2, 12), time = LocalTime.now()))
        transactionList.add(Transaction(title = "Lunch", category = "Food", amount = 70, date = LocalDate.of(2024, 2, 12), time = LocalTime.now()))

    }

}