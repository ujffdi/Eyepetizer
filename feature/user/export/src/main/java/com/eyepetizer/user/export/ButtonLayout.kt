package com.eyepetizer.user.export

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.eyepetizer.user.export.databinding.ItemEpoxyButtonBinding

/**
 * @author tongsr
 * @version 1.0
 * @date 2023/5/21
 * @email ujffdtfivkg@gmail.com
 * @description
 */
@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class ButtonLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr)  {

    init {
        inflate(context, R.layout.item_epoxy_button, this)
    }

    private val binding by viewBinding(ItemEpoxyButtonBinding::bind)

    @TextProp
    lateinit var text: CharSequence

    @AfterPropsSet
    fun useProps() {
        binding.btn.text = text
    }

}