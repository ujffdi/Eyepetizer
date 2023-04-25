package com.tongsr.common.widget.banner.indicator

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import com.tongsr.common.widget.banner.indicator.BannerIndicator

class RoundLinesIndicator @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BannerIndicator(context, attrs, defStyleAttr) {

    private val oval = RectF()

    private val rectF = RectF()

    init {
        paint.style = Paint.Style.FILL
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val count = indicatorSize
        if (count <= 1) return
        setMeasuredDimension((indicatorSelectedWidth * count), indicatorHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val count = indicatorSize
        if (count <= 1) return

        paint.color = indicatorNormalWidth
        oval.set(0F, 0F, width.toFloat(), indicatorHeight.toFloat())
        canvas.drawRoundRect(oval, indicatorRadius.toFloat(), indicatorRadius.toFloat(), paint)

        paint.color = indicatorSelectedColor
        val left = currentPosition * indicatorSelectedWidth
        rectF.set(
            left.toFloat(),
            0F,
            (left + indicatorSelectedWidth).toFloat(),
            indicatorHeight.toFloat()
        )
        canvas.drawRoundRect(rectF, indicatorRadius.toFloat(), indicatorRadius.toFloat(), paint)
    }

}