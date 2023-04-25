package com.tongsr.common.widget.banner.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import com.tongsr.common.widget.banner.holder.BannerImageHolder

/**
 * @author: tongsr
 * @date: 2023/3/14
 * @description BannerImageAdapter
 */
abstract class BannerImageAdapter<T>(items: List<T> = emptyList()) :
    BannerAdapter<T, BannerImageHolder>(items) {

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): BannerImageHolder {
        val imageView = ImageView(parent.context).apply {
            //注意，必须设置为match_parent，这个是viewpager2强制要求的
            val params = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            layoutParams = params
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
        return BannerImageHolder(imageView)
    }

}