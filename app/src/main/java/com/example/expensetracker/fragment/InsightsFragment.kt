package com.example.expensetracker.fragment

import android.os.Bundle
import android.util.AttributeSet
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensetracker.R
import com.example.expensetracker.activity.MainActivity
import com.example.expensetracker.adapter.InsightsAdapter
import com.example.expensetracker.databinding.FragmentInsightsBinding
import com.example.expensetracker.graph.PieChartView
import com.example.expensetracker.model.ExpenseCategory
import com.example.expensetracker.model.Transaction
import com.example.expensetracker.utils.TimeFrame
import com.example.expensetracker.viewmodel.ExpenseCategoryViewModel
import com.example.expensetracker.viewmodel.HomeViewModel
import com.example.expensetracker.viewmodel.TransactionViewModel

class InsightsFragment : Fragment() {

    private lateinit var binding: FragmentInsightsBinding
    private lateinit var transactionViewModel: TransactionViewModel
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var categoryViewModel: ExpenseCategoryViewModel
    private var categories: List<ExpenseCategory> = mutableListOf()
    private var weekAmountByCategory = mutableMapOf<String, Int>()
    private var monthAmountByCategory = mutableMapOf<String, Int>()
    private var yearAmountByCategory = mutableMapOf<String, Int>()
    private var monthGrandTotal: Int = 0
    private var weekGrandTotal: Int = 0
    private var yearGrandTotal: Int = 0
    private lateinit var insightsAdapter: InsightsAdapter
    private var selectedTimeFrame: TimeFrame = TimeFrame.WEEK

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInsightsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        transactionViewModel = (activity as MainActivity).viewModel
        categoryViewModel = (activity as MainActivity).expenseViewModel
        homeViewModel = (activity as MainActivity).homeViewModel
        selectedTimeFrame = TimeFrame.WEEK
        monitorTimeFrame()
        getWeeklyCategoryWiseExpense()
        getMonthlyCategoryWiseExpense()
        getYearlyCategoryWiseExpense()
        setupTimeFrameControls()
    }

    private fun getMonthlyCategoryWiseExpense() {
        homeViewModel.monthTransactionList.observe(viewLifecycleOwner) { transactionList ->
            val transactions = transactionList
            categoryViewModel.getAllExpenseCategory().observe(viewLifecycleOwner) { categoryList ->
                categories = categoryList
                val groupedTransactions = transactions.groupBy { it.category }
                groupedTransactions.forEach { (category, transactions) ->
                    val totalAmount = transactions.sumOf { it.amount.toInt() }
                    if (totalAmount != 0) {
                        monthAmountByCategory[category] = totalAmount
                    }
                    monthGrandTotal += totalAmount
                }
                if (selectedTimeFrame == TimeFrame.MONTH) {
                    setUpRecyclerview()
                    showPieChartView(monthAmountByCategory, monthGrandTotal)
                }
            }
        }
    }

    private fun getYearlyCategoryWiseExpense() {
        homeViewModel.yearTransactionList.observe(viewLifecycleOwner) { transactionList ->
            val transactions = transactionList
            categoryViewModel.getAllExpenseCategory().observe(viewLifecycleOwner) { categoryList ->
                categories = categoryList
                val groupedTransactions = transactions.groupBy { it.category }
                groupedTransactions.forEach { (category, transactions) ->
                    val totalAmount = transactions.sumOf { it.amount.toInt() }
                    if (totalAmount != 0) {
                        yearAmountByCategory[category] = totalAmount
                    }
                    yearGrandTotal += totalAmount
                }
                if (selectedTimeFrame == TimeFrame.YEAR) {
                    setUpRecyclerview()
                    showPieChartView(yearAmountByCategory, yearGrandTotal)
                }
            }
        }
    }

    private fun getWeeklyCategoryWiseExpense() {
        homeViewModel.weekTransactionList.observe(viewLifecycleOwner) { transactionList ->
            val transactions = transactionList
            categoryViewModel.getAllExpenseCategory().observe(viewLifecycleOwner) { categoryList ->
                categories = categoryList
                val groupedTransactions = transactions.groupBy { it.category }
                groupedTransactions.forEach { (category, transactions) ->
                    val totalAmount = transactions.sumOf { it.amount.toInt() }
                    if (totalAmount != 0) {
                        weekAmountByCategory[category] = totalAmount
                    }
                    weekGrandTotal += totalAmount
                }
                if (selectedTimeFrame == TimeFrame.WEEK) {
                    setUpRecyclerview()
                    showPieChartView(weekAmountByCategory, weekGrandTotal)
                }
            }
        }
    }

    private fun setUpRecyclerview() {
        insightsAdapter = InsightsAdapter()
        binding.insightsRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.insightsRecyclerview.adapter = insightsAdapter
        updateTimeFrameData()
    }

    private fun updateTimeFrameData() {
        when (selectedTimeFrame) {
            TimeFrame.WEEK -> insightsAdapter.updateData(
                weekAmountByCategory,
                weekGrandTotal,
                categories
            )
            TimeFrame.MONTH -> insightsAdapter.updateData(
                monthAmountByCategory,
                monthGrandTotal,
                categories
            )
            TimeFrame.YEAR -> insightsAdapter.updateData(
                yearAmountByCategory,
                yearGrandTotal,
                categories
            )
        }
    }

    private fun setupTimeFrameControls() {
        binding.btnThisWeek.setOnClickListener {
            selectedTimeFrame = TimeFrame.WEEK
            monitorTimeFrame()
            updateTimeFrameData()
            showPieChartView(weekAmountByCategory, weekGrandTotal)
        }
        binding.btnThisMonth.setOnClickListener {
            selectedTimeFrame = TimeFrame.MONTH
            monitorTimeFrame()
            updateTimeFrameData()
            showPieChartView(monthAmountByCategory, monthGrandTotal)

        }
        binding.btnThisYear.setOnClickListener {
            selectedTimeFrame = TimeFrame.YEAR
            monitorTimeFrame()
            updateTimeFrameData()
            showPieChartView(yearAmountByCategory, yearGrandTotal)
        }
    }

    private fun monitorTimeFrame() {
        when (selectedTimeFrame) {
            TimeFrame.WEEK -> {
                binding.btnThisWeek.setBackgroundColor(resources.getColor(R.color.black))
                binding.btnThisWeek.setTextColor(resources.getColor(R.color.white))
                binding.btnThisMonth.setBackgroundColor(resources.getColor(R.color.white))
                binding.btnThisMonth.setTextColor(resources.getColor(R.color.black))
                binding.btnThisYear.setBackgroundColor(resources.getColor(R.color.white))
                binding.btnThisYear.setTextColor(resources.getColor(R.color.black))
            }
            TimeFrame.MONTH -> {
                binding.btnThisWeek.setBackgroundColor(resources.getColor(R.color.white))
                binding.btnThisWeek.setTextColor(resources.getColor(R.color.black))
                binding.btnThisMonth.setBackgroundColor(resources.getColor(R.color.black))
                binding.btnThisMonth.setTextColor(resources.getColor(R.color.white))
                binding.btnThisYear.setBackgroundColor(resources.getColor(R.color.white))
                binding.btnThisYear.setTextColor(resources.getColor(R.color.black))
            }
            TimeFrame.YEAR -> {
                binding.btnThisWeek.setBackgroundColor(resources.getColor(R.color.white))
                binding.btnThisWeek.setTextColor(resources.getColor(R.color.black))
                binding.btnThisMonth.setBackgroundColor(resources.getColor(R.color.white))
                binding.btnThisMonth.setTextColor(resources.getColor(R.color.black))
                binding.btnThisYear.setBackgroundColor(resources.getColor(R.color.black))
                binding.btnThisYear.setTextColor(resources.getColor(R.color.white))
            }
        }
    }

    private fun showPieChartView(amountByCategory: MutableMap<String, Int>, totalAmount: Int) {
        /*Hidden for now*/
        //binding.pieChartView.updateValues(amountByCategory, totalAmount)
    }

}