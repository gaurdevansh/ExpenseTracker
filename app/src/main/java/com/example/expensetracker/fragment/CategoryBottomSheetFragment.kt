package com.example.expensetracker.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.expensetracker.R
import com.example.expensetracker.TransactionApplication
import com.example.expensetracker.adapter.CategoryAdapter
import com.example.expensetracker.databinding.CategoryBottomSheetBinding
import com.example.expensetracker.utils.GridSpacingItemDecoration
import com.example.expensetracker.viewmodel.ExpenseCategoryViewModel
import com.example.expensetracker.viewmodelFactory.ExpenseCategoryViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CategoryBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: CategoryBottomSheetBinding
    private lateinit var adapter: CategoryAdapter
    private val categoryViewModel: ExpenseCategoryViewModel by viewModels {
        ExpenseCategoryViewModelFactory((requireActivity().application as TransactionApplication).expenseCategoryRepository)
    }

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
        binding.categoryRecyclerview.addItemDecoration(GridSpacingItemDecoration(2, spacing, true))
        categoryViewModel.getAllExpenseCategory().observe(this, Observer { categoryList ->
            adapter.updateCategoryList(categoryList)
        })
        binding.backBtn.setOnClickListener { dismiss() }
    }
}