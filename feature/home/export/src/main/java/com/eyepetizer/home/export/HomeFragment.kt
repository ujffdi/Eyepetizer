package com.eyepetizer.home.export

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.angcyo.tablayout.delegate2.ViewPager2Delegate
import com.eyepetizer.home.export.databinding.FragmentHomeBinding
import com.eyepetizer.home.export.service.IFragmentService
import com.therouter.TheRouter
import com.tongsr.base.base.BaseFragment
import com.tongsr.common.adapter.GenericViewPager2Adapter
import com.tongsr.core.extend.clearDrawable
import com.tongsr.core.extend.setDrawableLeft

/**
 * @author tongsr
 * @version 1.0
 * @date 2023/5/3
 * @email ujffdtfivkg@gmail.com
 * @description 首页
 */
class HomeFragment : BaseFragment(), MavericksView {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by fragmentViewModel()

    override fun initData(bundle: Bundle?) {

    }

    override fun onBindLayout(): Int = R.layout.fragment_home

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        initDslTabLayout()
        initViewPager2()
    }

    override fun doBusiness() {

    }

    override fun invalidate() = withState(viewModel) { state ->

    }

    private fun initDslTabLayout() {
        binding.dslTabLayout.configTabLayoutConfig {

            onSelectViewChange =
                { fromView: View?, selectViewList: List<View>, reselect: Boolean, _: Boolean ->
                    if (reselect.not()) {
                        if (fromView is FrameLayout && fromView.getChildAt(0) is TextView) {
                            val textView = fromView.getChildAt(0)
                            (textView as TextView).clearDrawable()
                        }
                        val selectedView = selectViewList.first()
                        if (selectedView is FrameLayout && selectedView.getChildAt(0) is TextView) {
                            val textView = selectedView.getChildAt(0)
                            (textView as TextView).setDrawableLeft(R.drawable.ic_nav_indicator)
                        }
                    }
                }
        }
    }

    private fun initViewPager2() {
        val fragmentList = TheRouter.get(IFragmentService::class.java)?.getFragmentList()
        binding.viewPager2.adapter =
            GenericViewPager2Adapter(this, fragmentList = fragmentList ?: emptyList())
        ViewPager2Delegate.install(binding.viewPager2, binding.dslTabLayout)
    }

}
