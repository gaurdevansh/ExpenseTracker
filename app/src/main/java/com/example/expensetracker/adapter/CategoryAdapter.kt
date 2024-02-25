package com.example.expensetracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.databinding.CategoryItemBinding
import com.example.expensetracker.model.ExpenseCategory
import com.example.expensetracker.utils.AddCategoryListener
import com.example.expensetracker.utils.CustomItemClickListener
import com.example.expensetracker.utils.OnCloseListener

class CategoryAdapter(): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var categoryList: List<ExpenseCategory> = ArrayList()
    lateinit var clickListener: CustomItemClickListener
    lateinit var onCloseListener: OnCloseListener
    lateinit var addCategoryListener: AddCategoryListener

    inner class CategoryViewHolder(private val binding: CategoryItemBinding): RecyclerView.ViewHolder(binding.root) {
        val image = binding.categoryIcon
        val title = binding.tvCategory
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CategoryItemBinding.inflate(inflater, parent, false)
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoryList[position]
        holder.title.text = category.title
        holder.itemView.setOnClickListener {
            if (!holder.title.text.equals("Add Category")) {
                clickListener.onClick(category.title)
                onCloseListener.onClose()
            } else if (holder.title.text.equals("Add Category")) {
                addCategoryListener.onClick()
            }
        }
    }

    fun updateCategoryList(categoryList: List<ExpenseCategory>) {
        this.categoryList = categoryList
        this.notifyDataSetChanged()
    }
}