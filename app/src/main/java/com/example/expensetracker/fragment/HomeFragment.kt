package com.example.expensetracker.fragment

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensetracker.R
import com.example.expensetracker.activity.MainActivity
import com.example.expensetracker.adapter.TransactionAdapter
import com.example.expensetracker.databinding.FragmentHomeBinding
import com.example.expensetracker.utils.TimeFrame
import com.example.expensetracker.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: TransactionAdapter
    private var selectedTimeFrame: TimeFrame = TimeFrame.WEEK

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectedTimeFrame = TimeFrame.WEEK
        viewModel = (activity as MainActivity).homeViewModel
        viewModel.monthTotalExpense.observe(viewLifecycleOwner) { result ->
            binding.tvTotalMonthExpense.text = resources.getString(R.string.rupees_symbol) + result.toString()
        }
        viewModel.weekTotalExpense.observe(viewLifecycleOwner) { result ->
            binding.tvTotalWeekExpense.text = resources.getString(R.string.rupees_symbol) + result.toString()
        }
        viewModel.yearTotalExpense.observe(viewLifecycleOwner) { result ->
            binding.tvTotalYearExpense.text = resources.getString(R.string.rupees_symbol) + result.toString()
        }
        setUpRecyclerview()
        setupTimeFrameControls()
        monitorTimeFrame()
    }

    private fun setupTimeFrameControls() {
        binding.tvTotalMonthExpense.visibility = View.INVISIBLE
        binding.tvTotalYearExpense.visibility = View.INVISIBLE
        binding.btnThisWeek.setOnClickListener {
            selectedTimeFrame = TimeFrame.WEEK
            monitorTimeFrame()
        }
        binding.btnThisMonth.setOnClickListener {
            selectedTimeFrame = TimeFrame.MONTH
            monitorTimeFrame()
        }
        binding.btnThisYear.setOnClickListener {
            selectedTimeFrame = TimeFrame.YEAR
            monitorTimeFrame()
        }
    }

    private fun setUpRecyclerview() {
        binding.recentExpenseRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        adapter = TransactionAdapter()
        binding.recentExpenseRecyclerview.adapter = adapter
        viewModel.recentTransactions.observe(viewLifecycleOwner) { transactionList ->
            adapter.submitTransactionData(transactionList)
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
                binding.tvTotalWeekExpense.visibility = View.VISIBLE
                binding.tvTotalMonthExpense.visibility = View.INVISIBLE
                binding.tvTotalYearExpense.visibility = View.INVISIBLE
            }
            TimeFrame.MONTH -> {
                binding.btnThisWeek.setBackgroundColor(resources.getColor(R.color.white))
                binding.btnThisWeek.setTextColor(resources.getColor(R.color.black))
                binding.btnThisMonth.setBackgroundColor(resources.getColor(R.color.black))
                binding.btnThisMonth.setTextColor(resources.getColor(R.color.white))
                binding.btnThisYear.setBackgroundColor(resources.getColor(R.color.white))
                binding.btnThisYear.setTextColor(resources.getColor(R.color.black))
                binding.tvTotalWeekExpense.visibility = View.INVISIBLE
                binding.tvTotalMonthExpense.visibility = View.VISIBLE
                binding.tvTotalYearExpense.visibility = View.INVISIBLE
            }
            TimeFrame.YEAR -> {
                binding.btnThisWeek.setBackgroundColor(resources.getColor(R.color.white))
                binding.btnThisWeek.setTextColor(resources.getColor(R.color.black))
                binding.btnThisMonth.setBackgroundColor(resources.getColor(R.color.white))
                binding.btnThisMonth.setTextColor(resources.getColor(R.color.black))
                binding.btnThisYear.setBackgroundColor(resources.getColor(R.color.black))
                binding.btnThisYear.setTextColor(resources.getColor(R.color.white))
                binding.tvTotalWeekExpense.visibility = View.INVISIBLE
                binding.tvTotalMonthExpense.visibility = View.INVISIBLE
                binding.tvTotalYearExpense.visibility = View.VISIBLE
            }
        }
    }
}