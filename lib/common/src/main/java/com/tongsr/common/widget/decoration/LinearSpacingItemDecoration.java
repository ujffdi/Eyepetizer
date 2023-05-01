package com.tongsr.common.widget.decoration;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

/**
 * @author Tongsr
 * @version 1.0
 * @date 2020/6/9
 * @email ujffdtfivkg@gmail.com
 * @description LinearSpacingItemDecoration
 */
public final class LinearSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private final int spacing;

    /**
     * 间距相等
     * @param spacing 距离
     */
    public LinearSpacingItemDecoration(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, @NotNull View view,
                               RecyclerView parent, @NotNull RecyclerView.State state) {
        outRect.left = spacing;
        outRect.right = spacing;
        outRect.bottom = spacing;
        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = spacing;
        } else {
            outRect.top = 0;
        }
    }

}
