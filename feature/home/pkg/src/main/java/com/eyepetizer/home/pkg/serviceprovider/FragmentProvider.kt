package com.eyepetizer.home.pkg.serviceprovider

import androidx.fragment.app.Fragment
import com.eyepetizer.home.export.service.IFragmentService
import com.eyepetizer.home.pkg.ui.AttentionFragment
import com.eyepetizer.home.pkg.ui.dailypager.DailyPaperFragment
import com.eyepetizer.home.pkg.ui.RecommendFragment
import com.therouter.inject.ServiceProvider

/**
 * @author tongsr
 * @version 1.0
 * @date 2023/5/24
 * @email ujffdtfivkg@gmail.com
 * @description IFragmentService 的实现
 */

@ServiceProvider(returnType = IFragmentService::class)
fun getFragmentList(): IFragmentService = object : IFragmentService {
    override fun getFragmentList(): List<Fragment> {
        return listOf(
            RecommendFragment.newInstance(),
            AttentionFragment.newInstance(),
            DailyPaperFragment.newInstance()
        )
    }
}