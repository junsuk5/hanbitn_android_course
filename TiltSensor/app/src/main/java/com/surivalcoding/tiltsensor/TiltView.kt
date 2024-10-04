package com.surivalcoding.tiltsensor

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.hardware.SensorEvent
import android.view.View

class TiltView(context: Context) : View(context) {
    private val greenPaint = Paint()
    private val blackPaint = Paint()

    private var cX = 0f
    private var cY = 0f

    private var xCoord = 0f
    private var yCoord = 0f

    init {
        greenPaint.color = Color.GREEN
        blackPaint.style = Paint.Style.STROKE
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        cX = w / 2f
        cY = h / 2f
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(cX, cY, 100f, blackPaint)

        canvas.drawCircle(cX + xCoord, cY + yCoord, 100f, greenPaint)

        canvas.drawLine(cX - 20f, cY, cX + 20f, cY, blackPaint)
        canvas.drawLine(cX, cY - 20f, cX, cY + 20f, blackPaint)
    }

    fun onSensorEvent(event: SensorEvent) {
        yCoord = event.values[0] * 20
        xCoord = event.values[1] * 20
        invalidate()
    }
}