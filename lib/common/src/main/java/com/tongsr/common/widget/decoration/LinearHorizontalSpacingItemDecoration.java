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
 * @description LinearHorizontalSpacingItemDecoration
 */
public class LinearHorizontalSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private final int spacing;
    private boolean isTopAndBottom = false;

    /**
     * 间距相等
     * @param spacing 距离
     */
    public LinearHorizontalSpacingItemDecoration(int spacing, boolean isTopAndBottom) {
        this.spacing = spacing;
        this.isTopAndBottom = isTopAndBottom;
    }

    public LinearHorizontalSpacingItemDecoration(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(@NotNull Rect outRect, @NotNull View view,
                               @NotNull RecyclerView parent,
                               @NotNull RecyclerView.State state) {
        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.left = spacing;
        }
        outRect.right = spacing;
        if (isTopAndBottom) {
            outRect.top = spacing;
            outRect.bottom = spacing;
        }
    }

}
