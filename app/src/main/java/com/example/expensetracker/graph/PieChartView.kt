package com.example.expensetracker.graph

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.expensetracker.R
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class PieChartView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 80f
    }

    //private val colors = arrayOf(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN)
    private val colors = arrayOf(resources.getColor(R.color.dark_green), resources.getColor(R.color.dark_yellow), resources.getColor(R.color.dark_brown))
    private val data = floatArrayOf(20f, 30f, 50f)

    @SuppressLint("CanvasSize")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (canvas == null) return

        val width = canvas.width.toFloat()
        val height = canvas.height.toFloat()
        val radius = min(width, height) / 2 - paint.strokeWidth / 2
        val centerX = width / 2
        val centerY = height / 2
        val total = data.sum()

        var startAngle = -70f
        var gapAngle = 12f

        val semiCircleRadius = paint.strokeWidth * 0.1f - 7.5f


        for ((index, value) in data.withIndex()) {
            val sweepAngle = 360f * (value / total)
            paint.color = colors[index % colors.size]


            //Main Arc
            canvas.drawArc(
                centerX - radius, centerY - radius, centerX + radius, centerY + radius,
                startAngle + gapAngle , sweepAngle - gapAngle, false, paint
            )

            val startAngleRadians = Math.toRadians(startAngle.toDouble() + gapAngle.toDouble())
            val startX = centerX + radius * cos(startAngleRadians).toFloat()
            val startY = centerY + radius * sin(startAngleRadians).toFloat()

            //Semicircle at start
            canvas.drawArc(startX - semiCircleRadius, startY - semiCircleRadius, startX + semiCircleRadius, startY + semiCircleRadius,
                startAngle + gapAngle , -180f, false, paint)

            val endX = centerX + radius * cos(Math.toRadians(startAngle + sweepAngle.toDouble())).toFloat()
            val endY = centerY + radius * sin(Math.toRadians(startAngle + sweepAngle.toDouble())).toFloat()
            //Semicircle at end
            canvas.drawArc(endX - semiCircleRadius, endY - semiCircleRadius, endX + semiCircleRadius, endY + semiCircleRadius,
            startAngle + sweepAngle, 180f, false, paint)

            startAngle += sweepAngle
        }
    }
}
