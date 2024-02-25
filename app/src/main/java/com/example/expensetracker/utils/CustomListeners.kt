package com.example.expensetracker.utils

interface CustomItemClickListener {
    fun onClick(item: String)
}

interface OnCloseListener {
    fun onClose()
}

interface AddCategoryListener {
    fun onClick()
}