package com.tongsr.common.widget.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

/**
 * <pre>
 * author : Tongsr
 * time   : 2018/3/30
 * desc   :
 *
 */
/**
 * @author Tongsr
 * @version 1.0
 * @date 2020/6/8
 * @email ujffdtfivkg@gmail.com
 * @description 让recycler view grid下间距相等
 */
class GridSpacingItemDecoration
/**
 * 间距相等
 * @param spanCount spanCount
 * @param spacing 间距
 * @param includeEdge 是否设置边缘间距;false边缘有间距，true边缘无间距
 */(private val spanCount: Int, private val spacing: Int, private val includeEdge: Boolean) :
    ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view) // item position
        val column = position % spanCount // item column
        if (includeEdge) {
            outRect.left = column * spacing / spanCount // column * ((1f / spanCount) * spacing)
            outRect.right =
                spacing - (column + 1) * spacing / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = spacing // item top
            }
        } else {
            outRect.left =
                spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
            outRect.right =
                (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)
            if (position < spanCount) { // top edge
                outRect.top = spacing
            }
            outRect.bottom = spacing // item bottom
        }
    }
}