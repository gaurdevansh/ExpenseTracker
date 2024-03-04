package com.example.expensetracker.utils

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.example.expensetracker.R

enum class CustomColor(val colorId: Int, val value: Int) {
    RED(R.color.red_shade, 0),
    GREEN(R.color.green_shade, 1),
    BLUE(R.color.royal_blue, 2),
    YELLOW(R.color.dark_yellow, 3),
    PURPLE(R.color.red_purple, 4),
    VIOLET(R.color.violet, 5),
    ORANGE(R.color.orange, 6);

    companion object {

        val TOTAL_COLORS = 7

        fun getColor(context: Context, value: Int): Int {
            val customColor = values().find { it.value == value }
            return if (customColor != null) {
                return ContextCompat.getColor(context, customColor.colorId)
            } else {
                Color.CYAN
            }
        }
    }
}