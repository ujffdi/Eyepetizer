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
 * @description 日报
 */
class DailyPaperFragment : BaseFragment() {
    override fun initData(bundle: Bundle?) {

    }

    override fun onBindLayout(): Int = R.layout.fragment_daily_paper

    override fun initView(savedInstanceState: Bundle?, contentView: View) {

    }

    override fun doBusiness() {

    }

    companion object {

        @JvmStatic
        fun newInstance(): DailyPaperFragment {
            val args = Bundle()
            val fragment = DailyPaperFragment()
            fragment.arguments = args
            return fragment
        }
    }
}