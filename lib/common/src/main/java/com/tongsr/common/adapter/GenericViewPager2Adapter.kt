package com.tongsr.common.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * @author tongsr
 * @version 1.0
 * @date 2023/5/25
 * @email ujffdtfivkg@gmail.com
 * @description ViewPager2 通用的适配器
 */
class GenericViewPager2Adapter : FragmentStateAdapter {

    private val fragmentList: List<Fragment>

    constructor(fragment: Fragment, fragmentList: List<Fragment>) : super(fragment) {
        this.fragmentList = fragmentList
    }

    constructor(fragmentActivity: FragmentActivity, fragmentList: List<Fragment>) : super(
        fragmentActivity
    ) {
        this.fragmentList = fragmentList
    }

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

}