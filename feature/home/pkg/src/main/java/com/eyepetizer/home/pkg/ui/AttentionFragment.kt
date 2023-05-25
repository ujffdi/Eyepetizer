package com.eyepetizer.home.pkg.ui

import android.os.Bundle
import android.view.View
import com.eyepetizer.home.pkg.R
import com.tongsr.base.base.BaseFragment

/**
 * @author tongsr
 * @version 1.0
 * @date 2023/5/24
 * @email ujffdtfivkg@gmail.com
 * @description 关注
 */
class AttentionFragment : BaseFragment() {

    override fun initData(bundle: Bundle?) {

    }

    override fun onBindLayout(): Int = R.layout.fragment_attention

    override fun initView(savedInstanceState: Bundle?, contentView: View) {

    }

    override fun doBusiness() {

    }

    companion object {

        @JvmStatic
        fun newInstance(): AttentionFragment {
            val args = Bundle()
            val fragment = AttentionFragment()
            fragment.arguments = args
            return fragment
        }
    }
}