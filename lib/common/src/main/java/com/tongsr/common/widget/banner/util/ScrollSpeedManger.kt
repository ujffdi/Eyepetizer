package com.tongsr.common.widget.banner.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.tongsr.common.widget.banner.Banner

/**
 * 改变LinearLayoutManager的切换速度
 */
class ScrollSpeedManger(
    private val banner:
    Banner, linearLayoutManager: LinearLayoutManager
) : LinearLayoutManager(banner.context, linearLayoutManager.orientation, false) {

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView,
        state: RecyclerView.State,
        position: Int
    ) {
        val linearSmoothScroller: LinearSmoothScroller =
            object : LinearSmoothScroller(recyclerView.context) {
                override fun calculateTimeForDeceleration(dx: Int): Int {
                    return banner.scrollTime.toInt()
                }
            }
        linearSmoothScroller.targetPosition = position
        startSmoothScroll(linearSmoothScroller)
    }

}

fun Banner.reflectLayoutManager() {
    if (this.scrollTime < 100) return
    try {
        val viewPager2: ViewPager2 = this.viewPager2
        val recyclerView = viewPager2.getChildAt(0) as RecyclerView
        recyclerView.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        val speedManger =
            ScrollSpeedManger(this, recyclerView.layoutManager as LinearLayoutManager)
        recyclerView.layoutManager = speedManger
        val layoutMangerField = ViewPager2::class.java.getDeclaredField("mLayoutManager")
        layoutMangerField.isAccessible = true
        layoutMangerField[viewPager2] = speedManger
        val pageTransformerAdapterField =
            ViewPager2::class.java.getDeclaredField("mPageTransformerAdapter")
        pageTransformerAdapterField.isAccessible = true
        val mPageTransformerAdapter = pageTransformerAdapterField[viewPager2]
        if (mPageTransformerAdapter != null) {
            val aClass: Class<*> = mPageTransformerAdapter.javaClass
            val layoutManager = aClass.getDeclaredField("mLayoutManager")
            layoutManager.isAccessible = true
            layoutManager[mPageTransformerAdapter] = speedManger
        }
        val scrollEventAdapterField =
            ViewPager2::class.java.getDeclaredField("mScrollEventAdapter")
        scrollEventAdapterField.isAccessible = true
        val mScrollEventAdapter = scrollEventAdapterField[viewPager2]
        if (mScrollEventAdapter != null) {
            val aClass: Class<*> = mScrollEventAdapter.javaClass
            val layoutManager = aClass.getDeclaredField("mLayoutManager")
            layoutManager.isAccessible = true
            layoutManager[mScrollEventAdapter] = speedManger
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}