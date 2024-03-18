package com.example.expensetracker.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.AttributeSet
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensetracker.R
import com.example.expensetracker.activity.MainActivity
import com.example.expensetracker.adapter.InsightsAdapter
import com.example.expensetracker.databinding.FragmentInsightsBinding
import com.example.expensetracker.graph.PieChartView
import com.example.expensetracker.model.ExpenseCategory
import com.example.expensetracker.model.Transaction
import com.example.expensetracker.utils.DateUtils
import com.example.expensetracker.utils.TimeFrame
import com.example.expensetracker.viewmodel.ExpenseCategoryViewModel
import com.example.expensetracker.viewmodel.HomeViewModel
import com.example.expensetracker.viewmodel.TransactionViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Year
import java.time.YearMonth
import java.time.temporal.TemporalAdjusters
import java.util.Date

class InsightsFragment : Fragment() {

    private lateinit var binding: FragmentInsightsBinding
    private lateinit var transactionViewModel: TransactionViewModel
    private lateinit var categoryViewModel: ExpenseCategoryViewModel
    private lateinit var insightsAdapter: InsightsAdapter
    private var selectedTimeFrame: TimeFrame = TimeFrame.WEEK
    private var grandTotal = 0
    private var fromDate: LocalDate = DateUtils.getStartPeriod(LocalDate.now(), selectedTimeFrame)
    private var amountByCategory = mutableMapOf<String, Int>()
    private lateinit var allTransactionList: List<Transaction>
    private var transactionList: MutableList<Transaction> = mutableListOf()
    private var categoryList: MutableList<ExpenseCategory> = mutableListOf()
    private var dateToday: LocalDate = LocalDate.now()

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
        selectedTimeFrame = TimeFrame.WEEK

        fetchTransactionData()
        updateTimeFrameButtons()
        setupTimeFrameControls()

        binding.leftBtn.setOnClickListener {
            getTimeFrameDates(true)
        }
        binding.rightBtn.setOnClickListener {
            getTimeFrameDates(false)
        }
    }

    private fun fetchTransactionData() {
        categoryViewModel.getAllExpenseCategory().observe(viewLifecycleOwner) { categoryList ->
            this.categoryList = categoryList.toMutableList()
        }
        transactionViewModel.getTransactions(viewLifecycleOwner)
        transactionViewModel.allTransactionData.observe(viewLifecycleOwner) { transactionList ->
            this.allTransactionList = transactionList
            handleTransactions()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun handleTransactions() {
        grandTotal = 0
        amountByCategory.clear()
        transactionList.clear()
        transactionList = allTransactionList.filter { it.date in fromDate..dateToday }.toMutableList()
        val groupedTransaction = transactionList.groupBy { it.category }
        groupedTransaction.forEach { (category, transactions) ->
            val totalAmount = transactions.sumOf { it.amount.toInt() }
            if (totalAmount != 0) {
                amountByCategory[category] = totalAmount
            }
            grandTotal += totalAmount
        }
        setUpRecyclerview()
        showPieChartView(amountByCategory, grandTotal)
        binding.tvTotalAmount.text = " ${resources.getText(R.string.rupees_symbol)} $grandTotal"
        if (grandTotal == 0) {
            binding.tvTotalAmount.text = "No Expense in this\ntime frame"
        }
        updateDateViews()
    }

    private fun setUpRecyclerview() {
        if (!::insightsAdapter.isInitialized) {
            insightsAdapter = InsightsAdapter()
            binding.insightsRecyclerview.layoutManager = LinearLayoutManager(requireContext())
            binding.insightsRecyclerview.adapter = insightsAdapter
        }
        insightsAdapter.updateData(
            amountByCategory,
            grandTotal,
            categoryList,
            requireContext()
        )
    }

    private fun updateTimeFrameDates() {
        when (selectedTimeFrame) {
            TimeFrame.WEEK -> {
                fromDate = DateUtils.getStartPeriod(dateToday, TimeFrame.WEEK)
                handleTransactions()
            }
            TimeFrame.MONTH -> {
                fromDate = DateUtils.getStartPeriod(dateToday, TimeFrame.MONTH)
                handleTransactions()
            }
            TimeFrame.YEAR -> {
                fromDate = DateUtils.getStartPeriod(dateToday, TimeFrame.YEAR)
                handleTransactions()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupTimeFrameControls() {
        binding.btnWeek.setOnClickListener {
            selectedTimeFrame = TimeFrame.WEEK
            dateToday = LocalDate.now()
            updateTimeFrameButtons()
            updateTimeFrameDates()
        }
        binding.btnMonth.setOnClickListener {
            selectedTimeFrame = TimeFrame.MONTH
            dateToday = LocalDate.now()
            updateTimeFrameButtons()
            updateTimeFrameDates()
        }
        binding.btnYear.setOnClickListener {
            selectedTimeFrame = TimeFrame.YEAR
            dateToday = LocalDate.now()
            updateTimeFrameButtons()
            updateTimeFrameDates()
        }
    }

    private fun updateTimeFrameButtons() {
        when (selectedTimeFrame) {
            TimeFrame.WEEK -> {
                binding.btnWeek.setBackgroundColor(resources.getColor(R.color.black))
                binding.btnWeek.setTextColor(resources.getColor(R.color.white))
                binding.btnMonth.setBackgroundColor(resources.getColor(R.color.white))
                binding.btnMonth.setTextColor(resources.getColor(R.color.black))
                binding.btnYear.setBackgroundColor(resources.getColor(R.color.white))
                binding.btnYear.setTextColor(resources.getColor(R.color.black))
            }
            TimeFrame.MONTH -> {
                binding.btnWeek.setBackgroundColor(resources.getColor(R.color.white))
                binding.btnWeek.setTextColor(resources.getColor(R.color.black))
                binding.btnMonth.setBackgroundColor(resources.getColor(R.color.black))
                binding.btnMonth.setTextColor(resources.getColor(R.color.white))
                binding.btnYear.setBackgroundColor(resources.getColor(R.color.white))
                binding.btnYear.setTextColor(resources.getColor(R.color.black))
            }
            TimeFrame.YEAR -> {
                binding.btnWeek.setBackgroundColor(resources.getColor(R.color.white))
                binding.btnWeek.setTextColor(resources.getColor(R.color.black))
                binding.btnMonth.setBackgroundColor(resources.getColor(R.color.white))
                binding.btnMonth.setTextColor(resources.getColor(R.color.black))
                binding.btnYear.setBackgroundColor(resources.getColor(R.color.black))
                binding.btnYear.setTextColor(resources.getColor(R.color.white))
            }
        }
    }

    private fun showPieChartView(amountByCategory: MutableMap<String, Int>, totalAmount: Int) {
        binding.pieChartView.updateValues(amountByCategory, totalAmount)
    }

    private fun getTimeFrameDates(goBack: Boolean) {
        if (!goBack && dateToday == LocalDate.now()) {
            return
        } else if (goBack) {
            when (selectedTimeFrame) {
                TimeFrame.WEEK -> {
                    dateToday = fromDate.minusDays(1)
                    fromDate = DateUtils.getStartPeriod(dateToday, TimeFrame.WEEK)
                }
                TimeFrame.MONTH -> {
                    dateToday = fromDate.minusDays(1)
                    fromDate = DateUtils.getStartPeriod(dateToday, TimeFrame.MONTH)
                }
                TimeFrame.YEAR -> {
                    dateToday = fromDate.minusDays(1)
                    fromDate = DateUtils.getStartPeriod(dateToday, TimeFrame.YEAR)
                }
            }
            handleTransactions()
        } else {
            when (selectedTimeFrame) {
                TimeFrame.WEEK -> {
                    fromDate = dateToday.plusDays(1)
                    dateToday = fromDate.plusDays(6)
                }
                TimeFrame.MONTH -> {
                    fromDate = dateToday.plusDays(1)
                    dateToday = DateUtils.getEndPeriod(fromDate, TimeFrame.MONTH)
                }
                TimeFrame.YEAR -> {
                    fromDate = dateToday.plusDays(1)
                    dateToday = DateUtils.getEndPeriod(fromDate, TimeFrame.YEAR)
                }
            }
            handleTransactions()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateDateViews() {
        when(selectedTimeFrame) {
            TimeFrame.WEEK -> {
                if (fromDate == dateToday) {
                    binding.tvTime.text = "${dateToday.dayOfMonth} ${dateToday.month} ${dateToday.year}"
                } else {
                    binding.tvTime.text =
                        "${fromDate.dayOfMonth} - ${dateToday.dayOfMonth} ${dateToday.month} ${dateToday.year}"
                }
            }
            TimeFrame.MONTH -> {
                binding.tvTime.text = "${dateToday.month} ${dateToday.year}"
            }
            TimeFrame.YEAR -> {
                binding.tvTime.text = "${dateToday.year}"
            }
        }
    }

}