package com.tongsr.common.widget.banner.holder

import android.view.View
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

/**
 * @author: tongsr
 * @date: 2023/3/14
 * @description ImageView 类型的 ViewHolder
 */
class BannerImageHolder constructor(view: View) : RecyclerView.ViewHolder(view) {

    val imageView: ImageView = view as ImageView

}