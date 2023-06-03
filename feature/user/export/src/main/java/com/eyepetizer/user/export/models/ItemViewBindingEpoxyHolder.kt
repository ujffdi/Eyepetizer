package com.eyepetizer.user.export.models

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.eyepetizer.user.export.R
import com.eyepetizer.user.export.databinding.ItemEpoxyTextBinding
import com.tongsr.common.epoxy.ViewBindingEpoxyModelWithHolder

@EpoxyModelClass
abstract class ItemViewBindingEpoxyHolder :
    ViewBindingEpoxyModelWithHolder<ItemEpoxyTextBinding>() {

    @EpoxyAttribute
    lateinit var listener: () -> Unit

    @EpoxyAttribute
    lateinit var title: String

    override fun ItemEpoxyTextBinding.bind() {
        title.text = this@ItemViewBindingEpoxyHolder.title
        title.setOnClickListener { listener() }
    }

    override fun ItemEpoxyTextBinding.unbind() {
        // Don't leak listeners as this view goes back to the view pool
        title.setOnClickListener(null)
    }

    override fun getDefaultLayout(): Int = R.layout.item_epoxy_text

}
