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
 * @description LinearHorizontalSpacingItemDecoration
 */
class LinearHorizontalSpacingItemDecoration constructor(
    private val spacing: Int,
    private val includeEdge: Boolean = false
) : ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.left = spacing
        }
        outRect.right = spacing
        if (includeEdge) {
            outRect.top = spacing
            outRect.bottom = spacing
        }
    }
}