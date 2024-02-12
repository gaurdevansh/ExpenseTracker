package com.example.expensetracker

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.databinding.ActivityAddTransactionBinding
import com.example.expensetracker.model.Transaction
import com.example.expensetracker.viewmodel.TransactionViewModel
import com.example.expensetracker.viewmodelFactory.TransactionViewModelFactory
import java.time.LocalDate
import java.util.Calendar

class AddTransactionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTransactionBinding
    private val viewModel: TransactionViewModel by viewModels {
        TransactionViewModelFactory((application as TransactionApplication).repository)
    }
    private lateinit var selectedDate: LocalDate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var adapter = ArrayAdapter.createFromResource(
            this,
            R.array.spinner_items,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categorySpinner.adapter = adapter

        binding.tvSelectedDate.setOnClickListener {
            showDatePickerDialog()
        }
        binding.addBtn.setOnClickListener {
            checkInputs()
        }
    }

    private fun showDatePickerDialog() {
        var calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH)

        var datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view: DatePicker?, year: Int,
            month: Int, day: Int ->
                //val selectedDate = "$day/${month+1}/$year"
                selectedDate = LocalDate.of(year, month+1, day)
                binding.tvSelectedDate.text = selectedDate.toString()
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun checkInputs() {
        val title = binding.tvTitle.text.toString().trim()
        val category = binding.tvCategory.text.toString().trim()
        val amount = binding.tvAmount.text.toString().trim()
        val date = selectedDate
        if(title.isNotEmpty() && category.isNotEmpty() || amount.isNotEmpty()
            && date.toString().isNotEmpty()) {
            val transaction = Transaction(
                title = title, amount = amount, category = category,
                date = selectedDate
            )
            viewModel.insert(transaction)
            Toast.makeText(this, "Transaction Successfully Saved",
                Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}