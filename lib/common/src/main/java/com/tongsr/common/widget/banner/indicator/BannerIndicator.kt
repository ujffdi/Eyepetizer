package com.tongsr.common.widget.banner.indicator

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.viewpager.widget.ViewPager
import com.tongsr.common.R
import com.tongsr.common.widget.banner.config.*

/**
 * @author: tongsr
 * @date: 2023/3/20
 * @description Banner 的指示器
 */
open class BannerIndicator @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), ViewPager.OnPageChangeListener {

    protected val paint: Paint = Paint().apply {
        isAntiAlias = true
        color = Color.TRANSPARENT
        color = indicatorNormalColor
    }

    val indicatorView: View
        get() = this.apply {
            if (attachToBanner) {
                val layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                )
                when (indicatorGravity) {
                    Direction.LEFT -> layoutParams.gravity = Gravity.BOTTOM or Gravity.START
                    Direction.CENTER -> layoutParams.gravity =
                        Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
                    Direction.RIGHT -> layoutParams.gravity = Gravity.BOTTOM or Gravity.END
                }
                layoutParams.leftMargin = indicatorMarginLeft
                layoutParams.rightMargin = indicatorMarginRight
                layoutParams.topMargin = indicatorMarginTop
                layoutParams.bottomMargin = indicatorMarginBottom
                setLayoutParams(layoutParams)
            }
        }

    /**
     * 是否添加指示器
     */
    var attachToBanner = true

    /**
     * 指示器未选中的宽度
     */
    var indicatorNormalWidth: Int = INDICATOR_NORMAL_WIDTH

    /**
     * 指示器选中的宽度
     */
    var indicatorSelectedWidth: Int = INDICATOR_SELECTED_WIDTH

    /**
     * 指示器未选中的颜色
     */
    @ColorInt
    var indicatorNormalColor: Int = INDICATOR_NORMAL_COLOR

    /**
     * 指示器选中的颜色
     */
    @ColorInt
    var indicatorSelectedColor: Int = INDICATOR_SELECTED_COLOR

    /**
     * 指示器位置
     */
    @Direction
    var indicatorGravity: Int = Direction.CENTER
        set(value) {
            field = value
            indicatorView.postInvalidate()
        }

    /**
     * 指示器之间间距
     */
    var indicatorSpace = INDICATOR_SPACE

    /**
     * 指示器 Margin
     */
    var indicatorMargin = INDICATOR_MARGIN

    /**
     * 指示器左边 Margin
     */
    var indicatorMarginLeft = indicatorMargin

    /**
     * 指示器顶部 Margin
     */
    var indicatorMarginTop = indicatorMargin

    /**
     * 指示器右边 Margin
     */
    var indicatorMarginRight = indicatorMargin

    /**
     * 指示器底部 Margin
     */
    var indicatorMarginBottom = indicatorMargin

    /**
     * 指示器高度
     */
    var indicatorHeight: Int = INDICATOR_HEIGHT

    /**
     * 指示器圆角
     */
    var indicatorRadius: Int = INDICATOR_RADIUS

    /**
     * 指示器总大小
     */
    var indicatorSize = 0

    /**
     * 当前位置
     */
    var currentPosition = 0

    @SuppressLint("CustomViewStyleable")
    fun initAttribute(context: Context, attributeSet: AttributeSet?) {
        if (attributeSet == null) {
            return
        }
        val typed = context.obtainStyledAttributes(attributeSet, R.styleable.Banner)
        indicatorNormalWidth = typed.getDimensionPixelSize(R.styleable.Banner_banner_indicator_normal_width, INDICATOR_NORMAL_WIDTH)
        indicatorSelectedWidth = typed.getDimensionPixelSize(R.styleable.Banner_banner_indicator_selected_width, INDICATOR_SELECTED_WIDTH)
        indicatorNormalColor = typed.getColor(R.styleable.Banner_banner_indicator_normal_color, INDICATOR_NORMAL_COLOR)
        indicatorSelectedColor = typed.getColor(R.styleable.Banner_banner_indicator_selected_color, INDICATOR_SELECTED_COLOR)
        indicatorGravity = typed.getInt(R.styleable.Banner_banner_indicator_gravity, Direction.CENTER)
        indicatorSpace = typed.getDimensionPixelSize(R.styleable.Banner_banner_indicator_space, 0)
        indicatorMargin = typed.getDimensionPixelSize(R.styleable.Banner_banner_indicator_margin, 0)
        indicatorMarginLeft = typed.getDimensionPixelSize(R.styleable.Banner_banner_indicator_marginLeft, 0)
        indicatorMarginTop = typed.getDimensionPixelSize(R.styleable.Banner_banner_indicator_marginTop, 0)
        indicatorMarginRight = typed.getDimensionPixelSize(R.styleable.Banner_banner_indicator_marginRight, 0)
        indicatorMarginBottom = typed.getDimensionPixelSize(R.styleable.Banner_banner_indicator_marginBottom, 0)
        indicatorHeight = typed.getDimensionPixelSize(R.styleable.Banner_banner_indicator_height, INDICATOR_HEIGHT)
        indicatorRadius = typed.getDimensionPixelSize(R.styleable.Banner_banner_indicator_radius, INDICATOR_RADIUS)
        typed.recycle()
    }

    fun onPageChanged(count: Int, currentPosition: Int) {
        this.indicatorSize = count
        this.currentPosition = currentPosition
        requestLayout()
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        invalidate()
    }

    override fun onPageSelected(position: Int) {
        currentPosition = position
        invalidate()
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

}

