package com.example.expensetracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.model.Transaction

class TransactionAdapter(): RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    private var transactionList: List<Transaction> = ArrayList()
    inner class TransactionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById<TextView>(R.id.tvTitle)
        val category: TextView = itemView.findViewById<TextView>(R.id.tvCategory)
        val tvAmount: TextView = itemView.findViewById<TextView>(R.id.tvAmount)
        val date: TextView = itemView.findViewById<TextView>(R.id.tvDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.transaction_item_view, parent, false))
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val trans = transactionList[position]
        holder.title.text = trans.title
        holder.category.text = trans.category
        holder.tvAmount.text = trans.amount.toString()
       // holder.date.text = trans.date.toString()
    }

    fun submitTransactionData(transactionList: List<Transaction>) {
        this.transactionList = transactionList
        this.notifyDataSetChanged()
    }
}