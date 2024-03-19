package com.example.expensetracker.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.databinding.CategoryItemBinding
import com.example.expensetracker.utils.CustomItemClickListener
import com.example.expensetracker.utils.OnCloseListener

class InsightsCategoryAdapter() :
    RecyclerView.Adapter<InsightsCategoryAdapter.InsightsCategoryViewHolder>() {

    private var insightsCategoryList: List<String> = ArrayList()
    lateinit var clickListener: CustomItemClickListener
    lateinit var onCloseListener: OnCloseListener

    inner class InsightsCategoryViewHolder(private val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val title = binding.tvCategory
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InsightsCategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CategoryItemBinding.inflate(inflater, parent, false)
        return InsightsCategoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return insightsCategoryList.size
    }

    override fun onBindViewHolder(holder: InsightsCategoryViewHolder, position: Int) {
        val category = insightsCategoryList[position]
        holder.title.text = category
        holder.itemView.setOnClickListener {
            //clickListener.onClick(category)
            onCloseListener.onClose()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateCategoryList(categoryList: List<String>) {
        this.insightsCategoryList = categoryList
        this.notifyDataSetChanged()
    }
}
