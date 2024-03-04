package com.example.expensetracker.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.databinding.InsightsItemBinding
import com.example.expensetracker.model.ExpenseCategory
import com.example.expensetracker.utils.CustomColor
import kotlin.math.round

class InsightsAdapter() : RecyclerView.Adapter<InsightsAdapter.InsightsViewHolder>() {

    private var amountByCategory = mutableMapOf<String, Int>()
    private var categoryList: List<ExpenseCategory> = ArrayList()
    private var grandTotal = 0
    private lateinit var context: Context

    inner class InsightsViewHolder(private val binding: InsightsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
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
        return amountByCategory.size
    }

    override fun onBindViewHolder(holder: InsightsViewHolder, position: Int) {
        val (title, amount) = amountByCategory.entries.elementAt(position)
        holder.title.text = title
        holder.amount.text = "\u20B9" + amount.toString()
        holder.percent.text =
            (round((amount.toFloat() * 100) / grandTotal.toFloat())).toString() + "%"
        holder.progressBar.progressAmount =
            ((amount.toFloat() / grandTotal.toFloat()) * 100).toInt()
        holder.progressBar.setProgressColor(
            CustomColor.getColor(
                context,
                position % CustomColor.TOTAL_COLORS
            )
        )

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(
        amountByCategory: MutableMap<String, Int>,
        grandTotal: Int,
        categoryList: List<ExpenseCategory>,
        context: Context
    ) {
        this.amountByCategory = amountByCategory
        this.grandTotal = grandTotal
        this.categoryList = categoryList
        this.context = context
        notifyDataSetChanged()
    }
}