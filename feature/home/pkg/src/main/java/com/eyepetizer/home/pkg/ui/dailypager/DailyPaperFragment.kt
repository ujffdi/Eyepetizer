package com.eyepetizer.home.pkg.ui.dailypager

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.eyepetizer.home.pkg.R
import com.eyepetizer.home.pkg.databinding.FragmentDailyPaperBinding
import com.tongsr.base.mavericks.MavericksFragment

/**
 * @author tongsr
 * @version 1.0
 * @date 2023/5/24
 * @email ujffdtfivkg@gmail.com
 * @description 日报
 */
class DailyPaperFragment : MavericksFragment() {

    private val binding by viewBinding(FragmentDailyPaperBinding::bind)

    override fun initData(bundle: Bundle?) {

    }

    override fun onBindLayout(): Int = R.layout.fragment_daily_paper

    override fun initView(savedInstanceState: Bundle?, contentView: View) {

    }

    override fun doBusiness() {
        binding.epoxyRecyclerView.withModels {
            for (i in 0 until 50) {
//                itemVideoBindingEpoxyHolder {
//                    id(i)
//                    videoCoverUrl("https://img.eyepetizer.net/community/ugc-img/cover/1632406857021.jpeg?x-oss-process=image/resize,w_720")
//                    videoTitle("这是标题")
//                    videoDescription("这是描述")
//                    authorAvatarUrl("https://img.eyepetizer.net/community/avatar/1632406857021.jpeg?x-oss-process=image/resize,w_72")
//                    videoDuration("00:10")
//                }
            }
        }
    }

    override fun invalidate() {

    }

    companion object {

        @JvmStatic
        fun newInstance(): DailyPaperFragment {
            return DailyPaperFragment()
        }

    }

}