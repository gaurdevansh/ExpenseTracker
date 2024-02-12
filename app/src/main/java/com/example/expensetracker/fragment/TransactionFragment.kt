package com.example.expensetracker.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensetracker.TransactionAdapter
import com.example.expensetracker.TransactionApplication
import com.example.expensetracker.databinding.FragmentTransactionBinding
import com.example.expensetracker.viewmodel.TransactionViewModel
import com.example.expensetracker.viewmodelFactory.TransactionViewModelFactory


class TransactionFragment : Fragment() {

    private lateinit var binding: FragmentTransactionBinding
    //private val viewModel : TransactionViewModel by activityViewModels()
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
        binding.transactionRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        val adapter = TransactionAdapter()
        binding.transactionRecyclerview.adapter = adapter
        viewModel.getTransactions().observe(viewLifecycleOwner, Observer { transactionList ->
            transactionList?.let { adapter.submitTransactionData(it) }
            Log.d("****", "onViewCreated: ${transactionList.toString()}")
        })
    }

}