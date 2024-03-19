package com.example.expensetracker.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.expensetracker.R
import com.example.expensetracker.adapter.InsightsCategoryAdapter
import com.example.expensetracker.databinding.InsightsBottomSheetBinding
import com.example.expensetracker.utils.CustomItemClickListener
import com.example.expensetracker.utils.GridSpacingItemDecoration
import com.example.expensetracker.utils.OnCloseListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class InsightsBottomSheet : BottomSheetDialogFragment(), OnCloseListener {

    private lateinit var binding: InsightsBottomSheetBinding
    private lateinit var adapter: InsightsCategoryAdapter
    private var insightsList: MutableList<String> = arrayListOf()
    //lateinit var listener: CustomItemClickListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = InsightsBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spacing = resources.getDimensionPixelSize(R.dimen.spacing)
        adapter = InsightsCategoryAdapter()
        binding.categoryRecyclerview.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.categoryRecyclerview.adapter = adapter
        //adapter.clickListener = listener
        binding.categoryRecyclerview.addItemDecoration(GridSpacingItemDecoration(2, spacing, true))
        insightsList.add("Search")
        adapter.updateCategoryList(insightsList)
    }

    override fun onClose() {
        dismiss()
    }
}

