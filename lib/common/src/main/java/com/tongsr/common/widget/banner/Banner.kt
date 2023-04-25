package com.tongsr.common.widget.banner

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.IntDef
import androidx.annotation.RequiresApi
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.tongsr.common.R
import com.tongsr.core.extend.dp
import com.tongsr.common.widget.banner.adapter.BannerAdapter
import com.tongsr.common.widget.banner.config.IS_AUTO_LOOP
import com.tongsr.common.widget.banner.config.IS_INFINITE_LOOP
import com.tongsr.common.widget.banner.config.LOOP_TIME
import com.tongsr.common.widget.banner.config.SCROLL_TIME
import com.tongsr.common.widget.banner.util.getRealPosition
import com.tongsr.common.widget.banner.indicator.BannerIndicator
import com.tongsr.common.widget.banner.listener.OnPageChangeListener
import com.tongsr.common.widget.banner.util.setBannerRound
import com.tongsr.common.widget.banner.transformer.MZScaleInTransformer
import com.tongsr.common.widget.banner.transformer.ScaleInTransformer
import com.tongsr.common.widget.banner.util.reflectLayoutManager
import java.lang.ref.WeakReference
import kotlin.math.abs

class Banner @JvmOverloads constructor(
    context: Context,
    private val attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr), DefaultLifecycleObserver {

    /**
     * 适配器
     */
    var adapter: BannerAdapter<*, *>? = null
        private set

    /**
     * 轮播图核心组件
     */
    var viewPager2: ViewPager2
        private set

    /**
     * 指示器
     */
    lateinit var bannerIndicator: BannerIndicator

    /**
     * 自动轮播的实现
     */
    private val loopTask: AutoLoopTask
    private var onPageChangeListener: OnPageChangeListener? = null
    private var compositePageTransformer: CompositePageTransformer
    private val pageChangeCallback: BannerOnPageChangeCallback

    /**
     * 是否允许无限轮播（即首尾直接切换）
     */
    var isInfiniteLoop: Boolean = IS_INFINITE_LOOP

    /**
     * 是否自动轮播
     */
    var isAutoLoop: Boolean = IS_AUTO_LOOP

    /**
     * 轮播切换间隔时间
     */
    var loopTime: Long = LOOP_TIME.toLong()

    /**
     * 轮播切换时间
     */
    var scrollTime: Float = SCROLL_TIME

    /**
     * 设置开始的位置 (需要在setAdapter或者setList之前调用才有效)
     */
    var startPosition = 1

    /**
     * 改变最小滑动距离
     */
    var touchSlop = 0

    /**
     * 是否要拦截事件
     */
    var isIntercept = true

    /**
     * banner圆角半径，默认没有圆角
     */
    private var bannerRadius = 0f

    // banner圆角方向，如果一个都不设置，默认四个角全部圆角
    private var roundTopLeft = false
    private var roundTopRight = false
    private var roundBottomLeft = false
    private var roundBottomRight = false

    /**
     * 设置banner轮播方向
     */
    @Orientation
    var orientation = HORIZONTAL
        set(value) {
            field = value
            viewPager2.orientation = value
        }

    // 记录触摸的位置（主要用于解决事件冲突问题）
    private var startX = 0f
    private var startY = 0f

    /**
     * 记录viewpager2是否被拖动
     */
    private var isViewPager2Drag = false

    //绘制圆角视图
    private var roundPaint: Paint
    private var imagePaint: Paint

    @IntDef(HORIZONTAL, VERTICAL)
    @Retention(AnnotationRetention.SOURCE)
    annotation class Orientation

    init {
        touchSlop = ViewConfiguration.get(context).scaledTouchSlop / 2
        compositePageTransformer = CompositePageTransformer()
        pageChangeCallback = BannerOnPageChangeCallback()
        loopTask = AutoLoopTask(this)
        viewPager2 = ViewPager2(context)
        viewPager2.layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        viewPager2.offscreenPageLimit = 2
        viewPager2.registerOnPageChangeCallback(pageChangeCallback)
        viewPager2.setPageTransformer(compositePageTransformer)
        reflectLayoutManager()
        addView(viewPager2)

        roundPaint = Paint()
        roundPaint.color = Color.WHITE
        roundPaint.isAntiAlias = true
        roundPaint.style = Paint.Style.FILL
        roundPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
        imagePaint = Paint()
        imagePaint.xfermode = null

        val typed = context.obtainStyledAttributes(attributeSet, R.styleable.Banner)
        bannerRadius = typed.getDimensionPixelSize(R.styleable.Banner_banner_radius, 0).toFloat()
        loopTime = typed.getInt(R.styleable.Banner_banner_loop_time, LOOP_TIME).toLong()
        isAutoLoop = typed.getBoolean(R.styleable.Banner_banner_auto_loop, IS_AUTO_LOOP)
        isInfiniteLoop = typed.getBoolean(R.styleable.Banner_banner_infinite_loop, IS_INFINITE_LOOP)
        orientation = typed.getInt(R.styleable.Banner_banner_orientation, HORIZONTAL)
        roundTopLeft = typed.getBoolean(R.styleable.Banner_banner_round_top_left, false)
        roundTopRight = typed.getBoolean(R.styleable.Banner_banner_round_top_right, false)
        roundBottomLeft = typed.getBoolean(R.styleable.Banner_banner_round_bottom_left, false)
        roundBottomRight = typed.getBoolean(R.styleable.Banner_banner_round_bottom_right, false)

        typed.recycle()
        setInfiniteLoop()
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        // 根据手势操作开始或者停止轮播
        if (!viewPager2.isUserInputEnabled) {
            return super.dispatchTouchEvent(ev)
        }
        val action: Int = ev.actionMasked
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_OUTSIDE) {
            start()
        } else if (action == MotionEvent.ACTION_DOWN) {
            stop()
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        // 解决滑动冲突
        if (!viewPager2.isUserInputEnabled || !isIntercept) {
            return super.onInterceptTouchEvent(event)
        }
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = event.x
                startY = event.y
                parent.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                val endX: Float = event.x
                val endY: Float = event.y
                val distanceX = abs(endX - startX)
                val distanceY = abs(endY - startY)
                isViewPager2Drag = if (viewPager2.orientation == HORIZONTAL) {
                    distanceX > touchSlop && distanceX > distanceY
                } else {
                    distanceY > touchSlop && distanceY > distanceX
                }
                parent.requestDisallowInterceptTouchEvent(isViewPager2Drag)
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> parent.requestDisallowInterceptTouchEvent(
                false
            )
        }
        return super.onInterceptTouchEvent(event)
    }

    override fun dispatchDraw(canvas: Canvas) {
        if (bannerRadius > 0) {
            canvas.saveLayer(
                RectF(0F, 0F, canvas.width.toFloat(), canvas.height.toFloat()),
                imagePaint
            )
            super.dispatchDraw(canvas)
            //绘制外圆环边框圆环
            //默认四个角都设置
            if (!roundTopRight && !roundTopLeft && !roundBottomRight && !roundBottomLeft) {
                drawTopLeft(canvas)
                drawTopRight(canvas)
                drawBottomLeft(canvas)
                drawBottomRight(canvas)
                canvas.restore()
                return
            }
            if (roundTopLeft) {
                drawTopLeft(canvas)
            }
            if (roundTopRight) {
                drawTopRight(canvas)
            }
            if (roundBottomLeft) {
                drawBottomLeft(canvas)
            }
            if (roundBottomRight) {
                drawBottomRight(canvas)
            }
            canvas.restore()
        } else {
            super.dispatchDraw(canvas)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stop()
    }

    private fun drawTopLeft(canvas: Canvas) {
        val path = Path()
        path.moveTo(0f, bannerRadius)
        path.lineTo(0f, 0f)
        path.lineTo(bannerRadius, 0f)
        path.arcTo(RectF(0F, 0F, bannerRadius * 2, bannerRadius * 2), -90f, -90f)
        path.close()
        canvas.drawPath(path, roundPaint)
    }

    private fun drawTopRight(canvas: Canvas) {
        val width: Float = width.toFloat()
        val path = Path()
        path.moveTo(width - bannerRadius, 0f)
        path.lineTo(width, 0f)
        path.lineTo(width, bannerRadius)
        path.arcTo(RectF(width - 2 * bannerRadius, 0F, width, bannerRadius * 2), 0f, -90f)
        path.close()
        canvas.drawPath(path, roundPaint)
    }

    private fun drawBottomLeft(canvas: Canvas) {
        val height: Float = height.toFloat()
        val path = Path()
        path.moveTo(0f, height - bannerRadius)
        path.lineTo(0f, height)
        path.lineTo(bannerRadius, height)
        path.arcTo(RectF(0F, height - 2 * bannerRadius, bannerRadius * 2, height), 90f, 90f)
        path.close()
        canvas.drawPath(path, roundPaint)
    }

    private fun drawBottomRight(canvas: Canvas) {
        val height: Float = height.toFloat()
        val width: Float = width.toFloat()
        val path = Path()
        path.moveTo(width - bannerRadius, height)
        path.lineTo(width, height)
        path.lineTo(width, height - bannerRadius)
        path.arcTo(
            RectF(width - 2 * bannerRadius, height - 2 * bannerRadius, width, height),
            0f,
            90f
        )
        path.close()
        canvas.drawPath(path, roundPaint)
    }

    internal inner class BannerOnPageChangeCallback : ViewPager2.OnPageChangeCallback() {

        private var mTempPosition = INVALID_VALUE

        private var isScrolled = false

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            val realPosition = getRealPosition(isInfiniteLoop, position, getRealCount())
            if (onPageChangeListener != null && realPosition == currentItem - 1) {
                onPageChangeListener?.onPageScrolled(
                    realPosition,
                    positionOffset,
                    positionOffsetPixels
                )
            }
            if (::bannerIndicator.isInitialized && realPosition == currentItem - 1) {
                bannerIndicator.onPageScrolled(realPosition, positionOffset, positionOffsetPixels)
            }
        }

        override fun onPageSelected(position: Int) {
            if (isScrolled) {
                mTempPosition = position
                val realPosition = getRealPosition(isInfiniteLoop, position, getRealCount())
                if (onPageChangeListener != null) {
                    onPageChangeListener?.onPageSelected(realPosition)
                }

                if (::bannerIndicator.isInitialized) {
                    bannerIndicator.onPageSelected(realPosition)
                }
            }
        }

        override fun onPageScrollStateChanged(state: Int) {
            //手势滑动中,代码执行滑动中
            if (state == ViewPager2.SCROLL_STATE_DRAGGING || state == ViewPager2.SCROLL_STATE_SETTLING) {
                isScrolled = true
            } else if (state == ViewPager2.SCROLL_STATE_IDLE) {
                //滑动闲置或滑动结束
                isScrolled = false
                if (mTempPosition != INVALID_VALUE && isInfiniteLoop) {
                    if (mTempPosition == 0) {
                        setCurrentItem(getRealCount(), false)
                    } else if (mTempPosition == itemCount - 1) {
                        setCurrentItem(1, false)
                    }
                }
            }
            if (onPageChangeListener != null) {
                onPageChangeListener?.onPageScrollStateChanged(state)
            }
            if (::bannerIndicator.isInitialized) {
                bannerIndicator.onPageScrollStateChanged(state)
            }
        }
    }

    private val mAdapterDataObserver: RecyclerView.AdapterDataObserver =
        object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                if (itemCount <= 1) {
                    stop()
                } else {
                    start()
                }
                setIndicatorPageChange()
            }
        }

    private fun setInfiniteLoop() {
        // 当不支持无限循环时，要关闭自动轮播
        if (!isInfiniteLoop) {
            isAutoLoop = false
        }
        startPosition = if (isInfiniteLoop) startPosition else 0
    }

    private fun setRecyclerViewPadding(itemPadding: Int) {
        setRecyclerViewPadding(itemPadding, itemPadding)
    }

    private fun setRecyclerViewPadding(leftItemPadding: Int, rightItemPadding: Int) {
        val recyclerView = viewPager2.getChildAt(0) as RecyclerView
        if (viewPager2.orientation == ViewPager2.ORIENTATION_VERTICAL) {
            recyclerView.setPadding(
                viewPager2.paddingLeft,
                leftItemPadding,
                viewPager2.paddingRight,
                rightItemPadding
            )
        } else {
            recyclerView.setPadding(
                leftItemPadding,
                viewPager2.paddingTop,
                rightItemPadding,
                viewPager2.paddingBottom
            )
        }
        recyclerView.clipToPadding = false
    }

    /**
     * **********************************************************************
     * ------------------------ 对外公开API ---------------------------------*
     * **********************************************************************
     */

    /**
     * 获取当前 item
     */
    val currentItem
        get() = viewPager2.currentItem

    /**
     * 获取 item 数量
     */
    val itemCount
        get() = adapter?.itemCount ?: 0

    fun getRealCount(): Int = adapter?.getRealCount() ?: 0

    fun setIndicatorPageChange() {
        if (::bannerIndicator.isInitialized) {
            val realPosition = getRealPosition(isInfiniteLoop, currentItem, getRealCount())
            bannerIndicator.onPageChanged(getRealCount(), realPosition)
        }
    }

    /**
     * 跳转到指定位置（最好在设置了数据后在调用，不然没有意义）
     * @param position position
     */
    fun setCurrentItem(position: Int) {
        setCurrentItem(position, true)
    }

    /**
     * 跳转到指定位置（最好在设置了数据后在调用，不然没有意义）
     * @param position position
     * @param smoothScroll smoothScroll
     */
    fun setCurrentItem(position: Int, smoothScroll: Boolean) {
        viewPager2.setCurrentItem(position, smoothScroll)
    }

    /**
     * 删除指示器
     */
    fun removeIndicator() {
        if (::bannerIndicator.isInitialized) {
            removeView(bannerIndicator.indicatorView)
        }
    }

    /**
     * 禁止手动滑动
     *
     * @param enabled true 允许，false 禁止
     */
    fun setUserInputEnabled(enabled: Boolean) {
        viewPager2.isUserInputEnabled = enabled
    }

    /**
     * 添加PageTransformer，可以组合效果
     * [ViewPager2.PageTransformer]
     * 如果找不到请导入implementation "androidx.viewpager2:viewpager2:1.0.0"
     */
    fun addPageTransformer(transformer: ViewPager2.PageTransformer) {
        compositePageTransformer.addTransformer(transformer)
    }

    /**
     * 设置PageTransformer，和addPageTransformer不同，这个只支持一种transformer
     */
    fun setPageTransformer(transformer: ViewPager2.PageTransformer) {
        viewPager2.setPageTransformer(transformer)
    }

    /**
     * 删除 PageTransformer
     */
    fun removeTransformer(transformer: ViewPager2.PageTransformer) {
        compositePageTransformer.removeTransformer(transformer)
    }

    /**
     * 添加 ItemDecoration
     */
    fun addItemDecoration(decor: RecyclerView.ItemDecoration) {
        viewPager2.addItemDecoration(decor)
    }

    fun addItemDecoration(decor: RecyclerView.ItemDecoration, index: Int) {
        viewPager2.addItemDecoration(decor, index)
    }

    /**
     * 开始轮播
     */
    fun start() {
        if (isAutoLoop) {
            stop()
            postDelayed(loopTask, loopTime)
        }
    }

    /**
     * 停止轮播
     */
    fun stop() {
        if (isAutoLoop) {
            removeCallbacks(loopTask)
        }
    }

    /**
     * 移除一些引用
     */
    fun destroy() {
        viewPager2.unregisterOnPageChangeCallback(pageChangeCallback)
        adapter?.unregisterAdapterDataObserver(mAdapterDataObserver)
        stop()
    }

    /**
     * 设置banner的适配器
     */
    fun setAdapter(adapter: BannerAdapter<*, *>) {
        this.adapter = adapter
        if (!isInfiniteLoop) {
            adapter.increaseCount = 0
        }
        adapter.registerAdapterDataObserver(mAdapterDataObserver)
        viewPager2.adapter = adapter
        setCurrentItem(startPosition, false)
        setIndicatorPageChange()
    }

    /**
     * 添加viewpager切换事件
     *
     *
     * 在viewpager2中切换事件[ViewPager2.OnPageChangeCallback]是一个抽象类，
     * 为了方便使用习惯这里用的是和viewpager一样的 ViewPager.OnPageChangeListener 接口
     */
    fun addOnPageChangeListener(pageListener: OnPageChangeListener?) {
        onPageChangeListener = pageListener
    }

    /**
     * 设置banner圆角(第二种方式，和上面的方法不要同时使用)，只支持5.0以上
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    fun setBannerRound2(radius: Float) {
        setBannerRound(this, radius)
    }

    /**
     * 为banner添加画廊效果
     *
     * @param itemWidth  item左右展示的宽度,单位dp
     * @param pageMargin 页面间距,单位dp
     */
    fun setBannerGalleryEffect(itemWidth: Int, pageMargin: Int) {
        setBannerGalleryEffect(itemWidth, pageMargin, .85f)
    }

    /**
     * 为banner添加画廊效果
     *
     * @param leftItemWidth  item左展示的宽度,单位dp
     * @param rightItemWidth item右展示的宽度,单位dp
     * @param pageMargin     页面间距,单位dp
     */
    fun setBannerGalleryEffect(
        leftItemWidth: Int,
        rightItemWidth: Int,
        pageMargin: Int
    ) {
        setBannerGalleryEffect(leftItemWidth, rightItemWidth, pageMargin, .85f)
    }

    /**
     * 为banner添加画廊效果
     *
     * @param itemWidth  item左右展示的宽度,单位dp
     * @param pageMargin 页面间距,单位dp
     * @param scale      缩放[0-1],1代表不缩放
     */
    fun setBannerGalleryEffect(itemWidth: Int, pageMargin: Int, scale: Float) {
        setBannerGalleryEffect(itemWidth, itemWidth, pageMargin, scale)
    }

    /**
     * 为banner添加画廊效果
     *
     * @param leftItemWidth  item左展示的宽度,单位dp
     * @param rightItemWidth item右展示的宽度,单位dp
     * @param pageMargin     页面间距,单位dp
     * @param scale          缩放[0-1],1代表不缩放
     */
    fun setBannerGalleryEffect(
        leftItemWidth: Int,
        rightItemWidth: Int,
        pageMargin: Int,
        scale: Float
    ) {
        if (pageMargin > 0) {
            addPageTransformer(MarginPageTransformer(pageMargin))
        }
        if (scale < 1 && scale > 0) {
            addPageTransformer(ScaleInTransformer(scale))
        }
        setRecyclerViewPadding(
            if (leftItemWidth > 0) (leftItemWidth + pageMargin) else 0,
            if (rightItemWidth > 0) (rightItemWidth + pageMargin) else 0
        )
    }

    /**
     * 为banner添加魅族效果
     *
     * @param itemWidth item左右展示的宽度,单位dp
     */
    fun setBannerGalleryMZ(itemWidth: Int) {
        setBannerGalleryMZ(itemWidth, .88f)
    }

    /**
     * 为banner添加魅族效果
     *
     * @param itemWidth item左右展示的宽度,单位dp
     * @param scale     缩放[0-1],1代表不缩放
     */
    fun setBannerGalleryMZ(itemWidth: Int, scale: Float) {
        if (scale < 1 && scale > 0) {
            addPageTransformer(MZScaleInTransformer(scale))
        }
        setRecyclerViewPadding(itemWidth.dp.toInt())
    }
    /**
     * **********************************************************************
     * ------------------------ 指示器相关设置 --------------------------------*
     * **********************************************************************
     */

    /**
     * 设置轮播指示器(如果你的指示器写在布局文件中，attachToBanner 传 false )
     *
     * @param attachToBanner 是否将指示器添加到 Banner 中，false 代表你可以将指示器通过布局放在任何位置
     * 注意：设置为 false 后，内置的 indicatorGravity() 和 indicatorMargins() 方法将失效。
     * 想改变可以自己调用系统提供的属性在布局文件中进行设置。具体可以参照demo
     */
    @JvmOverloads
    fun setIndicator(indicator: BannerIndicator, attachToBanner: Boolean = true) {
        if (indicator.attachToBanner) {
            removeIndicator()
            addView(indicator.indicatorView)
        }
        bannerIndicator = indicator
        bannerIndicator.attachToBanner = attachToBanner
        bannerIndicator.initAttribute(context, attributeSet)
        setIndicatorPageChange()
    }

    /**
     * **********************************************************************
     * ------------------------ 生命周期控制 --------------------------------*
     * **********************************************************************
     */

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        start()
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        stop()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        destroy()
    }

    companion object {

        const val INVALID_VALUE = -1

        const val HORIZONTAL = 0

        const val VERTICAL = 1

    }

}

/**
 * 轮播执行器
 */
class AutoLoopTask(banner: Banner) : Runnable {

    private val reference = WeakReference(banner)

    override fun run() {
        val banner: Banner? = reference.get()
        if (banner != null && banner.isAutoLoop) {
            val count = banner.itemCount
            if (count == 0) {
                return
            }
            val next: Int = (banner.currentItem + 1) % count
            banner.setCurrentItem(next)
            banner.postDelayed(this, banner.loopTime)
        }
    }

}

