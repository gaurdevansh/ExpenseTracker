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
import com.example.expensetracker.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: TransactionAdapter
    private var selectedBtn = 0

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
        selectedBtn = 0
        viewModel = (activity as MainActivity).homeViewModel
        viewModel.monthTotalExpense.observe(viewLifecycleOwner) { result ->
            binding.tvTotalMonthExpense.text = resources.getString(R.string.rupees_symbol) + result.toString()
        }
        viewModel.weekTotalExpense.observe(viewLifecycleOwner) { result ->
            binding.tvTotalWeekExpense.text = resources.getString(R.string.rupees_symbol) + result.toString()
        }
        setUpRecyclerview()
        setUpDateControls()
        monitorDateControls()
    }

    private fun setUpDateControls() {
        binding.tvTotalWeekExpense.visibility = View.INVISIBLE
        binding.btnThisMonth.setOnClickListener {
            selectedBtn = 0
            monitorDateControls()
            binding.tvTotalMonthExpense.visibility = View.VISIBLE
            binding.tvTotalWeekExpense.visibility = View.INVISIBLE
        }
        binding.btnThisWeek.setOnClickListener {
            selectedBtn = 1
            monitorDateControls()
            binding.tvTotalMonthExpense.visibility = View.INVISIBLE
            binding.tvTotalWeekExpense.visibility = View.VISIBLE
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

    private fun monitorDateControls() {
        if (selectedBtn == 0) {
            binding.btnThisMonth.setBackgroundColor(resources.getColor(R.color.black))
            binding.btnThisMonth.setTextColor(resources.getColor(R.color.white))
            binding.btnThisWeek.setBackgroundColor(resources.getColor(R.color.white))
            binding.btnThisWeek.setTextColor(resources.getColor(R.color.black))
        } else {
            binding.btnThisMonth.setBackgroundColor(resources.getColor(R.color.white))
            binding.btnThisMonth.setTextColor(resources.getColor(R.color.black))
            binding.btnThisWeek.setBackgroundColor(resources.getColor(R.color.black))
            binding.btnThisWeek.setTextColor(resources.getColor(R.color.white))
        }
    }

}