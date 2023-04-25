package com.tongsr.common.widget.banner.indicator

import android.content.Context
import android.util.AttributeSet
import android.graphics.Canvas

/**
 * 圆形指示器
 * 如果想要大小一样，可以将选中和默认设置成同样大小
 */
class CircleIndicator @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BannerIndicator(context, attrs, defStyleAttr) {

    private var mNormalRadius = indicatorNormalWidth / 2
    private var mSelectedRadius = indicatorSelectedWidth / 2
    private var maxRadius = 0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val count: Int = indicatorSize
        if (count <= 1) {
            return
        }
        mNormalRadius = indicatorNormalWidth / 2
        mSelectedRadius = indicatorSelectedWidth / 2
        //考虑当 选中和默认 的大小不一样的情况
        maxRadius = mSelectedRadius.coerceAtLeast(mNormalRadius)
        //间距*（总数-1）+选中宽度+默认宽度*（总数-1）
        val width: Int =
            (count - 1) * indicatorSpace + indicatorSelectedWidth + indicatorNormalWidth * (count - 1)
        setMeasuredDimension(
            width,
            indicatorNormalWidth.coerceAtLeast(indicatorSelectedWidth)
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val count: Int = indicatorSize
        if (count <= 1) {
            return
        }
        var left = 0f
        for (i in 0 until count) {
            paint.color = if (currentPosition == i) indicatorSelectedWidth else indicatorNormalWidth
            val indicatorWidth: Int = if (currentPosition == i) indicatorSelectedWidth else indicatorNormalWidth
            val radius = if (currentPosition == i) mSelectedRadius else mNormalRadius
            canvas.drawCircle(left + radius, maxRadius.toFloat(), radius.toFloat(), paint)
            left += indicatorWidth + indicatorSpace
        }
    }

}