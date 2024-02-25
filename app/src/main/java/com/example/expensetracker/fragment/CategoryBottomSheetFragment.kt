package com.example.expensetracker.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.expensetracker.R
import com.example.expensetracker.TransactionApplication
import com.example.expensetracker.adapter.CategoryAdapter
import com.example.expensetracker.databinding.CategoryBottomSheetBinding
import com.example.expensetracker.model.ExpenseCategory
import com.example.expensetracker.utils.AddCategoryListener
import com.example.expensetracker.utils.CustomItemClickListener
import com.example.expensetracker.utils.GridSpacingItemDecoration
import com.example.expensetracker.utils.OnCloseListener
import com.example.expensetracker.viewmodel.ExpenseCategoryViewModel
import com.example.expensetracker.viewmodelFactory.ExpenseCategoryViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CategoryBottomSheetFragment : BottomSheetDialogFragment(), OnCloseListener, AddCategoryListener {

    private lateinit var binding: CategoryBottomSheetBinding
    private lateinit var adapter: CategoryAdapter
    private var updatedList: MutableList<ExpenseCategory> = mutableListOf()
    private val categoryViewModel: ExpenseCategoryViewModel by viewModels {
        ExpenseCategoryViewModelFactory((requireActivity().application as TransactionApplication).expenseCategoryRepository)
    }
    lateinit var listener: CustomItemClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CategoryBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spacing = resources.getDimensionPixelSize(R.dimen.spacing)
        adapter = CategoryAdapter()
        binding.categoryRecyclerview.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.categoryRecyclerview.adapter = adapter
        adapter.clickListener = listener
        adapter.onCloseListener = this
        adapter.addCategoryListener = this
        binding.categoryRecyclerview.addItemDecoration(GridSpacingItemDecoration(2, spacing, true))
        categoryViewModel.getAllExpenseCategory().observe(this, Observer { categoryList ->
            updatedList.clear()
            categoryList.forEach {
                updatedList.add(it)
            }
            updatedList.add(ExpenseCategory(id = 9999, title = "Add Category"))
            adapter.updateCategoryList(updatedList)
        })
        binding.backBtn.setOnClickListener { dismiss() }
    }

    override fun onClose() {
        dismiss()
    }

    override fun onClick() {
        openAddCategoryDialog()
    }

    private fun openAddCategoryDialog() {
        val editText = EditText(context)
        editText.hint = "Enter Category"

        val alertDialog = AlertDialog.Builder(context)
            .setTitle("Enter Category")
            .setView(editText)
            .setPositiveButton("Save") { _: DialogInterface, _: Int ->
                val enteredCategory = editText.text.toString()
                val expenseCategory = ExpenseCategory(title = enteredCategory)
                categoryViewModel.insert(expenseCategory)
                adapter.notifyDataSetChanged()
            }
            .setNegativeButton("Cancel") { dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
            }
            .create()

        alertDialog.show()
    }
}