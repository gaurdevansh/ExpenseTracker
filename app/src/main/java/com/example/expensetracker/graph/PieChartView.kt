package com.example.expensetracker.graph

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

class PieChartView(context: Context, attrs: AttributeSet): View(context, attrs) {

    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 100f
    }

    private val colors = arrayOf(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN)
    private val data = floatArrayOf(20f, 30f, 15f, 10f, 25f)

    @SuppressLint("CanvasSize")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if(canvas == null) return

        val width = canvas.width.toFloat()
        val height = canvas.height.toFloat()
        val radius = min(width, height) / 2 - paint.strokeWidth / 2
        val centerX = width / 2
        val centerY = height / 2
        val total = data.sum()

        var startAngle = -90f

        for((index, value) in data.withIndex()) {
            val sweepAngle = 360f * (value / total)
            paint.color = colors[index % colors.size]

            val gapAngle = 10f
            val gapStartAngle = startAngle + gapAngle / 2
            val gapEndAngle = startAngle + sweepAngle - gapAngle / 2
            val gapRadius = radius + paint.strokeWidth / 2
            val semiCircleRadius = gapAngle / 2
            val semiCircleRect = RectF(centerX + radius, centerY + gapRadius + paint.strokeWidth/2, centerX + radius + paint.strokeWidth, centerY + gapRadius)

            //Main Arc
            canvas.drawArc(centerX - radius, centerY - radius, centerX + radius, centerY + radius
                , startAngle + gapAngle/2, sweepAngle-gapAngle, false, paint)

            //Arc around the start of the gap
//            canvas.drawArc(centerX - gapRadius, centerY - gapRadius, centerX + gapRadius,  centerY + gapRadius,
//            gapStartAngle, gapAngle / 2, false, paint)
            canvas.drawArc(semiCircleRect, 0f, 180f, false, paint)
            //Arc around the end of the gap
//            canvas.drawArc(centerX - gapRadius, centerY - gapRadius, centerX + gapRadius,  centerY + gapRadius,
//            gapEndAngle - gapAngle / 2, gapAngle / 2, false, paint)
            //canvas.drawArc(semiCircleRect, startAngle + sweepAngle - semiCircleRadius, semiCircleRadius, false, paint)
            startAngle += sweepAngle
        }

    }

}