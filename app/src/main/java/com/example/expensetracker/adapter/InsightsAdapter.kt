package com.example.expensetracker.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.databinding.InsightsItemBinding
import com.example.expensetracker.model.ExpenseCategory

class InsightsAdapter(): RecyclerView.Adapter<InsightsAdapter.InsightsViewHolder>() {

    private var amountByCategory = mutableMapOf<String, Int>()
    private var categoryList: List<ExpenseCategory> = ArrayList()
    private var grandTotal = 0

    inner class InsightsViewHolder(private val binding: InsightsItemBinding): RecyclerView.ViewHolder(binding.root) {
        val image = binding.imageIcon
        val title = binding.tvTitle
        val percent = binding.tvPercent
        val amount = binding.tvAmount
        val progressBar = binding.progressbar
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InsightsViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val binding = InsightsItemBinding.inflate(inflator, parent, false)
        return InsightsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: InsightsViewHolder, position: Int) {
        val index = categoryList[position]
        val item = amountByCategory[index.title] ?: 0
        if(item != 0) {
            holder.title.text = index.title
            holder.amount.text = "\u20B9" + item.toString()
            holder.percent.text = ((item*100) / grandTotal).toString() + "%"
            holder.progressBar.progressAmount = ((item.toFloat() / grandTotal.toFloat()) * 100).toInt()
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(amountByCategory: MutableMap<String, Int>, grandTotal: Int, categoryList: List<ExpenseCategory>) {
        this.amountByCategory = amountByCategory
        this.grandTotal = grandTotal
        this.categoryList = categoryList
        notifyDataSetChanged()
    }
}