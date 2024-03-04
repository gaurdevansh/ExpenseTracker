package com.example.expensetracker.graph

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.expensetracker.R
import com.example.expensetracker.utils.CustomColor
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class PieChartView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private var amountByCategory = mutableMapOf<String, Int>()
    private var totalAmount = 0

    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 60f
    }

    /*private val colors = arrayOf(
        resources.getColor(R.color.royal_blue),
        resources.getColor(R.color.red_shade),
        resources.getColor(R.color.green_shade),
        resources.getColor(R.color.violet),
        resources.getColor(R.color.orange),
        resources.getColor(R.color.dark_yellow),
        resources.getColor(R.color.red_purple)
        )*/

    @SuppressLint("CanvasSize")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)


        if (canvas == null) return

        val width = canvas.width.toFloat()
        val height = canvas.height.toFloat()
        val radius = min(width, height) / 2 - paint.strokeWidth / 2
        val centerX = width / 2
        val centerY = height / 2

        var startAngle = -70f
        var gapAngle = 1f

        //val semiCircleRadius = paint.strokeWidth * 0.1f - 5.5f
        var index = 0

        for ((_, value) in amountByCategory) {
            val sweepAngle = 360f * (value.toFloat() / totalAmount.toFloat())
            paint.color = CustomColor.getColor(context, index % CustomColor.TOTAL_COLORS)

            //Main Arc
            canvas.drawArc(
                centerX - radius, centerY - radius, centerX + radius, centerY + radius,
                startAngle, sweepAngle - gapAngle, false, paint
            )

            //Segment end semi circle code
            /*val startAngleRadians = Math.toRadians(startAngle.toDouble() + gapAngle.toDouble())
            val startX = centerX + radius * cos(startAngleRadians).toFloat()
            val startY = centerY + radius * sin(startAngleRadians).toFloat()

            //Semicircle at start
            canvas.drawArc(
                startX - semiCircleRadius,
                startY - semiCircleRadius,
                startX + semiCircleRadius,
                startY + semiCircleRadius,
                startAngle + gapAngle,
                -180f,
                false,
                paint
            )

            val endX =
                centerX + radius * cos(Math.toRadians(startAngle + sweepAngle.toDouble())).toFloat()
            val endY =
                centerY + radius * sin(Math.toRadians(startAngle + sweepAngle.toDouble())).toFloat()
            //Semicircle at end
            canvas.drawArc(
                endX - semiCircleRadius,
                endY - semiCircleRadius,
                endX + semiCircleRadius,
                endY + semiCircleRadius,
                startAngle + sweepAngle,
                180f,
                false,
                paint
            )*/

            startAngle += sweepAngle
            index++
        }
    }

    fun updateValues(amountByCategory: MutableMap<String, Int>, totalAmount: Int) {
        this.amountByCategory = amountByCategory
        this.totalAmount = totalAmount
        invalidate()
    }
}
