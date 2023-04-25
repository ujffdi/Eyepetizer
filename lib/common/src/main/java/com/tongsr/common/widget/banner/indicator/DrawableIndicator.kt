package com.tongsr.common.widget.banner.indicator

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import com.tongsr.common.R

/**
 * Drawable指示器
 */
class DrawableIndicator : BannerIndicator {

    private var normalBitmap: Bitmap
    private var selectedBitmap: Bitmap

    /**
     * 实例化Drawable指示器 ，也可以通过自定义属性设置
     * @param context
     * @param normalResId
     * @param selectedResId
     */
    constructor(
        context: Context,
        @DrawableRes normalResId: Int,
        @DrawableRes selectedResId: Int
    ) : super(context) {
        normalBitmap = BitmapFactory.decodeResource(resources, normalResId)
        selectedBitmap = BitmapFactory.decodeResource(resources, selectedResId)
    }

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DrawableIndicator)
        val normal: BitmapDrawable =
            typedArray.getDrawable(R.styleable.DrawableIndicator_normal_drawable) as BitmapDrawable
        val selected: BitmapDrawable =
            typedArray.getDrawable(R.styleable.DrawableIndicator_selected_drawable) as BitmapDrawable
        normalBitmap = normal.bitmap
        selectedBitmap = selected.bitmap
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val count = indicatorSize
        if (count <= 1) {
            return
        }
        setMeasuredDimension(
            selectedBitmap.width * (count - 1) + selectedBitmap.width + indicatorSpace * (count - 1),
            normalBitmap.height.coerceAtLeast(selectedBitmap.height)
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val count = indicatorSize
        if (count <= 1) {
            return
        }
        var left = 0f
        for (i in 0 until count) {
            canvas.drawBitmap(
                if (currentPosition == i) selectedBitmap else normalBitmap,
                left,
                0f,
                paint
            )
            left += (normalBitmap.width + indicatorSpace).toFloat()
        }
    }
}