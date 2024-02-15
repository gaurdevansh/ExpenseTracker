package com.example.expensetracker.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensetracker.R
import com.example.expensetracker.adapter.TransactionAdapter
import com.example.expensetracker.TransactionApplication
import com.example.expensetracker.databinding.FragmentTransactionBinding
import com.example.expensetracker.viewmodel.TransactionViewModel
import com.example.expensetracker.viewmodelFactory.TransactionViewModelFactory
import java.time.LocalDate
import java.util.*


class TransactionFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentTransactionBinding
    //private val viewModel : TransactionViewModel by activityViewModels()
    private val viewModel: TransactionViewModel by viewModels {
        TransactionViewModelFactory((requireActivity().application as TransactionApplication).repository)
    }
    private lateinit var fromDate: LocalDate
    private lateinit var toDate: LocalDate

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
        /*viewModel.getTransactions().observe(viewLifecycleOwner, Observer { transactionList ->
            transactionList?.let { adapter.submitTransactionData(it) }
        })*/
        viewModel.getTransactionByDate(LocalDate.now(), LocalDate.now()).observe(viewLifecycleOwner, Observer { transactionList ->
            transactionList?.let { adapter.submitTransactionData(it) }
        })
        fromDate = LocalDate.now()
        toDate = LocalDate.now()
        binding.fromDateSelector.selectorLayout.setOnClickListener(this)
        binding.toDateSelector.selectorLayout.setOnClickListener(this)
        binding.goBtn.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.fromDateSelector -> {
                showDatePickerDialog("from")
            }
            R.id.toDateSelector -> {
                showDatePickerDialog("to")
            }
            R.id.goBtn -> {
                viewModel.getTransactionByDate(fromDate, toDate)
            }
        }
    }

    private fun showDatePickerDialog(type: String) {
        var calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH)

        var datePickerDialog = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { view: DatePicker?, year: Int,
                                                 month: Int, day: Int ->
                if(type == "from") {
                    fromDate = LocalDate.of(year, month + 1, day)
                    binding.fromDateSelector.tvSelectedDate.text = fromDate.toString()
                } else {
                    toDate = LocalDate.of(year, month + 1, day)
                    binding.toDateSelector.tvSelectedDate.text = toDate.toString()
                }
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

}