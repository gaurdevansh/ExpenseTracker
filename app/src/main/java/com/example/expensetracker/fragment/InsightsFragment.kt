package com.example.expensetracker.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensetracker.R
import com.example.expensetracker.activity.MainActivity
import com.example.expensetracker.adapter.InsightsAdapter
import com.example.expensetracker.databinding.FragmentGraphBinding
import com.example.expensetracker.model.ExpenseCategory
import com.example.expensetracker.model.Transaction
import com.example.expensetracker.viewmodel.ExpenseCategoryViewModel
import com.example.expensetracker.viewmodel.TransactionViewModel
import com.github.mikephil.charting.charts.PieChart

class InsightsFragment : Fragment() {

    private lateinit var binding: FragmentGraphBinding
    private lateinit var transactionViewModel: TransactionViewModel
    private lateinit var categoryViewModel: ExpenseCategoryViewModel
    private lateinit var transactions: List<Transaction>
    private var categories: List<ExpenseCategory> = mutableListOf()
    private var amountByCategory = mutableMapOf<String, Int>()
    private var grandTotal: Int = 0
    private var selectedBtn = 0
    private lateinit var insightsAdapter: InsightsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGraphBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        transactionViewModel = (activity as MainActivity).viewModel
        categoryViewModel = (activity as MainActivity).expenseViewModel
        getCategoryWiseExpense()
        monitorDateButtons()
        binding.btnThisMonth.setOnClickListener {
            selectedBtn = 0
            monitorDateButtons()
        }
        binding.btnThisYear.setOnClickListener {
            selectedBtn = 1
            monitorDateButtons()
        }
    }

    private fun getCategoryWiseExpense() {
        transactionViewModel.getTransactions().observe(viewLifecycleOwner) { transactionList ->
            transactions = transactionList
            categoryViewModel.getAllExpenseCategory().observe(viewLifecycleOwner) { categoryList ->
                categories = categoryList
                val groupedTransactions = transactions.groupBy { it.category }
                groupedTransactions.forEach { (category, transactions) ->
                    val totalAmount = transactions.sumOf { it.amount.toInt() }
                    amountByCategory[category] = totalAmount
                    grandTotal += totalAmount
                }
                setUpRecyclerview()
            }
        }
    }

    private fun setUpRecyclerview() {
        insightsAdapter = InsightsAdapter()
        binding.insightsRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.insightsRecyclerview.adapter = insightsAdapter
        insightsAdapter.updateData(amountByCategory, grandTotal, categories)
    }

    private fun monitorDateButtons() {
        if (selectedBtn == 0) {
            binding.btnThisMonth.setBackgroundColor(resources.getColor(R.color.black))
            binding.btnThisMonth.setTextColor(resources.getColor(R.color.white))
            binding.btnThisYear.setBackgroundColor(resources.getColor(R.color.white))
            binding.btnThisYear.setTextColor(resources.getColor(R.color.black))
        } else {
            binding.btnThisMonth.setBackgroundColor(resources.getColor(R.color.white))
            binding.btnThisMonth.setTextColor(resources.getColor(R.color.black))
            binding.btnThisYear.setBackgroundColor(resources.getColor(R.color.black))
            binding.btnThisYear.setTextColor(resources.getColor(R.color.white))
        }
    }

}