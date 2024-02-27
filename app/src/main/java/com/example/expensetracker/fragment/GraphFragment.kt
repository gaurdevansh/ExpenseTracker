package com.example.expensetracker.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.expensetracker.R
import com.example.expensetracker.activity.MainActivity
import com.example.expensetracker.databinding.FragmentGraphBinding
import com.example.expensetracker.model.ExpenseCategory
import com.example.expensetracker.model.Transaction
import com.example.expensetracker.viewmodel.ExpenseCategoryViewModel
import com.example.expensetracker.viewmodel.TransactionViewModel
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

class GraphFragment : Fragment() {

    private lateinit var pieChart: PieChart
    private lateinit var binding: FragmentGraphBinding
    private lateinit var transactionViewModel: TransactionViewModel
    private lateinit var categoryViewModel: ExpenseCategoryViewModel
    private lateinit var transactions: List<Transaction>
    private lateinit var categories: List<ExpenseCategory>
    private var amountByCategory = mutableMapOf<String, Int>()

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
                }
                //setUpPieChart()
            }
        }
    }

    private fun setUpPieChart() {
        //pieChart = binding.pieChart

       /* pieChart.setUsePercentValues(true)
        pieChart.description.isEnabled = false
        pieChart.setExtraOffsets(5f, 10f, 5f, 5f)
        pieChart.dragDecelerationFrictionCoef = 0.95f
        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(Color.WHITE)
        pieChart.setTransparentCircleColor(Color.WHITE)
        pieChart.setTransparentCircleAlpha(110)
        pieChart.holeRadius = 58f
        pieChart.transparentCircleRadius = 61f
        pieChart.setDrawCenterText(true)
        pieChart.rotationAngle = 0f
        pieChart.isRotationEnabled = true
        pieChart.isHighlightPerTapEnabled = true

        // Set data
        val entries = arrayListOf<PieEntry>()
        for(x in amountByCategory) {
            entries.add(PieEntry(x.value.toFloat(), x.key))
        }
       *//* entries.add(PieEntry(18f, "Label 1"))
        entries.add(PieEntry(20f, "Label 2"))
        entries.add(PieEntry(22f, "Label 3"))
        entries.add(PieEntry(24f, "Label 4"))
        entries.add(PieEntry(16f, "Label 5"))*//*

        val dataSet = PieDataSet(entries, "Data")
        dataSet.colors = ColorTemplate.JOYFUL_COLORS.toList()
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f

        val data = PieData(dataSet)
        data.setValueTextSize(10f)
        data.setValueTextColor(Color.WHITE)

        pieChart.data = data
        pieChart.invalidate()*/
    }

}