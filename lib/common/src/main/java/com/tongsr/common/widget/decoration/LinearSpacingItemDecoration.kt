package com.tongsr.common.widget.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

/**
 * @author Tongsr
 * @version 1.0
 * @date 2020/6/9
 * @email ujffdtfivkg@gmail.com
 * @description LinearSpacingItemDecoration
 */
class LinearSpacingItemDecoration
/**
 * 间距相等
 * @param spacing 距离
 */
constructor(private val spacing: Int) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.left = spacing
        outRect.right = spacing
        outRect.bottom = spacing
        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = spacing
        } else {
            outRect.top = 0
        }
    }
}