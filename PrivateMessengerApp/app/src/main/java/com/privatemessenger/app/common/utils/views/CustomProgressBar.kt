package com.privatemessenger.app.common.utils.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.privatemessenger.app.R
import kotlin.random.Random

class CustomProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var progress: Int = 0
    private val totalBars: Int = 40
    private val barWidth: Float = 8f
    private val spacing: Float = 4f
    private val paint = Paint()

    init {
        paint.color = ContextCompat.getColor(context, R.color.progress_bar_gray)
    }

    fun setProgress(progress: Int) {
        this.progress = progress.coerceIn(0, 100)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val startX = 0f
        val centerY = height / 2f

        for (i in 0 until totalBars) {
            val currentX = startX + i * (barWidth + spacing)
            val randomHeight = Random.nextInt(20, height / 2 + 1)
            paint.color = if (i < progress / (100 / totalBars)) ContextCompat.getColor(context, R.color.progress_bar_blue) else ContextCompat.getColor(context, R.color.progress_bar_gray)

            canvas.drawRect(
                currentX,
                centerY - randomHeight.toFloat(),
                currentX + barWidth,
                centerY + randomHeight.toFloat(),
                paint
            )
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(
            MeasureSpec.getSize(widthMeasureSpec),
            MeasureSpec.getSize(heightMeasureSpec)
        )
    }
}



