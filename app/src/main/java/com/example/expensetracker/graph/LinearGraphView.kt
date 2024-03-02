package com.example.expensetracker.graph

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.expensetracker.R

class LinearGraphView(context: Context, attrs: AttributeSet): View(context, attrs) {

    private val backgroundPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 30f
        color = resources.getColor(R.color.light_gray)
    }

    private val progressPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 40f
        color = resources.getColor(R.color.dark_yellow)
    }
    var progressAmount: Int = 55

    init {
        getContext().theme.obtainStyledAttributes(
            attrs,
            R.styleable.LinearGraphView,
            0, 0
        ).apply {
            try {
                val progressColor = getColor(
                    R.styleable.LinearGraphView_progressColor,
                    resources.getColor(R.color.dark_yellow)
                )
                progressPaint.color = progressColor
            } finally {
                recycle()
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if(canvas == null) return

        val width = canvas.width.toFloat()

        canvas.drawLine(0f, 0f, width, 0f, backgroundPaint)

        var newWidth = width * (progressAmount / 100f)
        canvas.drawLine(0f, -5f, newWidth, -5f, progressPaint)
    }

    fun setProgressColor(color: Int) {
        progressPaint.color = color
    }
}