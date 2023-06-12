package com.eyepetizer.home.export.models

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.eyepetizer.home.export.R
import com.eyepetizer.home.export.databinding.ItemEpoxyVideoBinding
import com.tongsr.common.epoxy.ViewBindingEpoxyModelWithHolder
import com.tongsr.core.util.ToastUtils

/**
 * @author tongsr
 * @version 1.0
 * @date 2023/6/12
 * @email ujffdtfivkg@gmail.com
 * @description Video Item
 */
@EpoxyModelClass
abstract class ItemVideoBindingEpoxyHolder :
    ViewBindingEpoxyModelWithHolder<ItemEpoxyVideoBinding>() {

    /**
     * 视频封面
     */
    @EpoxyAttribute
    lateinit var videoCoverUrl: String

    /**
     * 视频标题
     */
    @EpoxyAttribute
    lateinit var videoTitle: String

    /**
     * 视频描述
     */
    @EpoxyAttribute
    lateinit var videoDescription: String

    /**
     * 视频作者头像
     */
    @EpoxyAttribute
    lateinit var authorAvatarUrl: String

    /**
     * 视频时长
     */
    @EpoxyAttribute
    lateinit var videoDuration: String

    override fun getDefaultLayout(): Int {
        return R.layout.item_epoxy_video
    }

    override fun ItemEpoxyVideoBinding.bind() {
        clRoot.setOnClickListener {
            ToastUtils.showShort("点击了视频")
        }
    }

}