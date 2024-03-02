package com.example.expensetracker.adapter

import android.app.ActionBar.LayoutParams
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.R
import com.example.expensetracker.databinding.TransactionItemViewBinding
import com.example.expensetracker.model.Transaction
import com.example.expensetracker.utils.DateUtils

class TransactionAdapter(): RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    private var transactionList: List<Transaction> = ArrayList()
    inner class TransactionViewHolder(private val binding: TransactionItemViewBinding): RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.tvTitle
        //val category: TextView = itemView.findViewById<TextView>(R.id.tvCategory)
        val tvAmount: TextView = itemView.findViewById<TextView>(R.id.tvAmount)
        val date: TextView = itemView.findViewById<TextView>(R.id.tvDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TransactionItemViewBinding.inflate(inflater, parent, false)
        return TransactionViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val trans = transactionList[position]
        holder.title.text = trans.title
        //holder.category.text = trans.category
        holder.tvAmount.text = "\u20B9" + trans.amount
        holder.date.text = "Paid on " + DateUtils.getDateInFormat(trans.date)
    }

    fun submitTransactionData(transactionList: List<Transaction>) {
        this.transactionList = transactionList
        notifyDataSetChanged()
    }
}