package com.tongsr.common.paging3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tongsr.core.extend.getLayoutInflater

/**
 * @author Tongsr
 * @version 1.0
 * @date 2022/8/10
 * @email ujffdtfivkg@gmail.com
 * @description 实现传递 LayoutInflater
 */
abstract class BasePagingDataBinderAdapter<T : Any, VH : RecyclerView.ViewHolder> constructor(diffCallback: DiffUtil.ItemCallback<T>) :
    BasePagingDataAdapter<T, VH>(diffCallback) {

    override fun onCreateBaseViewHolder(parent: ViewGroup, viewType: Int): VH {
        return onCreateBaseViewHolder(parent.getLayoutInflater(), parent)
    }

    abstract fun onCreateBaseViewHolder(inflater: LayoutInflater, parent: ViewGroup): VH

}