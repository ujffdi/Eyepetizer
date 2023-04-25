package com.tongsr.common.widget.banner.indicator

import android.content.Context
import android.graphics.Canvas
import android.graphics.RectF
import android.util.AttributeSet

/**
 * 矩形（条形）指示器
 * 1、可以设置选中和默认的宽度、指示器的圆角
 * 2、如果需要正方形将圆角设置为0，可将宽度和高度设置为一样
 * 3、如果不想选中时变长，可将选中的宽度和默认宽度设置为一样
 */
class RectangleIndicator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BannerIndicator(context, attrs, defStyleAttr) {

    private val rectF = RectF()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val count = indicatorSize
        if (count <= 1) {
            return
        }
        //间距*（总数-1）+默认宽度*（总数-1）+选中宽度
        val space = indicatorSpace * (count - 1)
        val normal = indicatorNormalWidth * (count - 1)
        setMeasuredDimension(space + normal + indicatorSelectedWidth, indicatorHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val count = indicatorSize
        if (count <= 1) {
            return
        }
        var left = 0f
        for (i in 0 until count) {
            paint.color = if (currentPosition == i) indicatorSelectedColor else indicatorNormalColor
            val indicatorWidth =
                if (currentPosition == i) indicatorSelectedWidth else indicatorNormalWidth
            rectF[left, 0f, left + indicatorWidth] = indicatorHeight.toFloat()
            left += (indicatorWidth + indicatorSpace).toFloat()
            canvas.drawRoundRect(rectF, indicatorRadius.toFloat(), indicatorRadius.toFloat(), paint)
        }
    }

}