package com.tongsr.common.widget.banner.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tongsr.common.widget.banner.config.INCREASE_COUNT
import com.tongsr.common.widget.banner.util.getRealPosition


/**
 * @author: tongsr
 * @date: 2023/3/14
 * @description BannerAdapter
 */
abstract class BannerAdapter<T, VH : RecyclerView.ViewHolder>(open var items: List<T> = emptyList()) :
    RecyclerView.Adapter<VH>() {

    /**
     * Banner 点击事件
     */
    private var onBannerClickListener: OnBannerClickListener<T>? = null

    var increaseCount: Int = INCREASE_COUNT

    final override fun onBindViewHolder(holder: VH, position: Int) {
        val item: T = items[getRealPosition(position)]
        onBindViewHolder(holder, position, item)
    }

    final override fun onBindViewHolder(holder: VH, position: Int, payloads: MutableList<Any>) {
        val item: T = items[getRealPosition(position)]
        onBindViewHolder(holder, position, item)
    }

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return onCreateViewHolder(parent.context, parent, viewType).apply {
            bindViewClickListener(this, viewType)
        }
    }

    override fun getItemCount(): Int =
        if (getRealCount() > 1) getRealCount() + increaseCount else getRealCount()

    protected abstract fun onBindViewHolder(holder: VH, position: Int, item: T?)

    protected abstract fun onCreateViewHolder(
        context: Context, parent: ViewGroup, viewType: Int
    ): VH

    /**
     * 设置 Banner 数据，考虑到很少场景需要动态增加删除 Banner。所以没有 add 等方法
     * @param list banner data
     */
    open fun submitList(list: List<T>?) {
        if (list === items) return
        val newList = list ?: emptyList()
        items = newList
        notifyDataSetChanged()
    }

    open fun getRealCount(): Int = items.size

    open fun getRealPosition(position: Int): Int = getRealPosition(increaseCount == INCREASE_COUNT, position, getRealCount())

    fun setOnItemClickListener(listener: OnBannerClickListener<T>?) = apply {
        this.onBannerClickListener = listener
    }

    protected open fun onItemClick(v: View, position: Int) {
        onBannerClickListener?.onClick(this, v, position)
    }

    protected open fun bindViewClickListener(viewHolder: VH, viewType: Int) {
        onBannerClickListener?.let {
            viewHolder.itemView.setOnClickListener { v ->
                val position = viewHolder.bindingAdapterPosition
                if (position == RecyclerView.NO_POSITION) {
                    return@setOnClickListener
                }
                onItemClick(v, position)
            }
        }
    }

    fun interface OnBannerClickListener<T> {
        fun onClick(adapter: BannerAdapter<T, *>, view: View, position: Int)
    }

}