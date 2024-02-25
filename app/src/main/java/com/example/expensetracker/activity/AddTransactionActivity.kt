package com.example.expensetracker.activity

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.expensetracker.R
import com.example.expensetracker.TransactionApplication
import com.example.expensetracker.databinding.ActivityAddTransactionBinding
import com.example.expensetracker.fragment.CategoryBottomSheetFragment
import com.example.expensetracker.model.ExpenseCategory
import com.example.expensetracker.model.Transaction
import com.example.expensetracker.utils.CustomItemClickListener
import com.example.expensetracker.viewmodel.ExpenseCategoryViewModel
import com.example.expensetracker.viewmodel.TransactionViewModel
import com.example.expensetracker.viewmodelFactory.ExpenseCategoryViewModelFactory
import com.example.expensetracker.viewmodelFactory.TransactionViewModelFactory
import java.time.LocalDate
import java.util.Calendar

class AddTransactionActivity : AppCompatActivity(), CustomItemClickListener {

    private lateinit var binding: ActivityAddTransactionBinding
    private val viewModel: TransactionViewModel by viewModels {
        TransactionViewModelFactory((application as TransactionApplication).repository)
    }
    private val categoryViewModel: ExpenseCategoryViewModel by viewModels {
        ExpenseCategoryViewModelFactory((application as TransactionApplication).expenseCategoryRepository)
    }
    private lateinit var selectedDate: LocalDate
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var categoryBottomSheet: CategoryBottomSheetFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item
        )
        adapter.clear()
        categoryViewModel.getAllExpenseCategory().observe(this, Observer { categoryList ->
            val list = categoryList.map { it.title }
            adapter.addAll(list)
        })
        adapter.notifyDataSetChanged()
        binding.addBtn.setBackgroundColor(resources.getColor(R.color.black))
        binding.backBtn.setOnClickListener { finish() }
        binding.etCategory.setOnClickListener {
            categoryBottomSheet = CategoryBottomSheetFragment()
            categoryBottomSheet.listener = this
            categoryBottomSheet.show(supportFragmentManager, categoryBottomSheet.tag)
        }
        binding.etDate.setOnClickListener {
            showDatePickerDialog()
        }
        binding.etDate.setText(LocalDate.now().toString())
        selectedDate = LocalDate.now()
        binding.addBtn.setOnClickListener { checkInputs() }

        /*adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categorySpinner.adapter = adapter

        binding.dateSelector.selectorLayout.setOnClickListener {
            showDatePickerDialog()
        }
        binding.addBtn.setOnClickListener {
            checkInputs()
        }
        binding.menuBtn.setOnClickListener {
            openPopupMenu()
        }
        binding.dateSelector.tvSelectedDate.text = LocalDate.now().toString()
        selectedDate = LocalDate.now()*/
    }

    private fun showDatePickerDialog() {
        var calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH)

        var datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _: DatePicker?, year: Int,
            month: Int, day: Int ->
                //val selectedDate = "$day/${month+1}/$year"
                selectedDate = LocalDate.of(year, month+1, day)
                binding.etDate.setText(selectedDate.toString())
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun checkInputs() {
        val title = binding.etDescription.text.toString().trim()
        val category = binding.etCategory.text.toString().trim()
        val amount = binding.etAmount.text.toString().trim()
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
        } else {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openPopupMenu() {
        /*val popupMenu = PopupMenu(this, binding.menuBtn)
        popupMenu.inflate(R.menu.add_transaction_menu)

        popupMenu.setOnMenuItemClickListener { menuItem: MenuItem ->
            when(menuItem.itemId) {
                R.id.category -> {
                    showAddCategoryDialog()
                    true
                }
                R.id.subCategory -> {

                    true
                }
                else -> false
            }
        }
        popupMenu.show()*/
    }

    override fun onClick(item: String) {
        binding.etCategory.setText(item.toString())
    }
}