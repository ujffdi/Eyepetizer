package com.eyepetizer.home.pkg.ui.dailypager

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.eyepetizer.home.export.models.itemVideoBindingEpoxyHolder
import com.eyepetizer.home.pkg.R
import com.eyepetizer.home.pkg.databinding.FragmentDailyPaperBinding
import com.tongsr.base.mavericks.MavericksFragment
import com.tongsr.core.component.EndlessRecyclerViewScrollListener
import com.tongsr.core.util.LogUtils

/**
 * @author tongsr
 * @version 1.0
 * @date 2023/5/24
 * @email ujffdtfivkg@gmail.com
 * @description 日报
 */
class DailyPaperFragment : MavericksFragment() {

    private val binding by viewBinding(FragmentDailyPaperBinding::bind)

    private val items = mutableListOf<String>()
    override fun initData(bundle: Bundle?) {

    }

    override fun onBindLayout(): Int = R.layout.fragment_daily_paper

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        addData()
        binding.epoxyRecyclerView.addOnScrollListener(object :
            EndlessRecyclerViewScrollListener(binding.epoxyRecyclerView.layoutManager as LinearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                addData()
                binding.epoxyRecyclerView.requestModelBuild()
            }
        })
    }

    override fun doBusiness() {
        binding.epoxyRecyclerView.withModels {
            LogUtils.e("withModels ------${items.size}")
            for (i in 0 until items.size) {
                itemVideoBindingEpoxyHolder {
                    id(i)
                    videoCoverUrl("https://img.eyepetizer.net/community/ugc-img/cover/1632406857021.jpeg?x-oss-process=image/resize,w_720")
                    videoTitle("这是标题")
                    videoDescription("这是描述")
                    authorAvatarUrl("https://img.eyepetizer.net/community/avatar/1632406857021.jpeg?x-oss-process=image/resize,w_72")
                    videoDuration("00:10")
                }
            }
        }
    }

    override fun invalidate() {

    }

    private fun addData() {
        for (i in 0 until 10) {
            items.add("item $i")
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(): DailyPaperFragment {
            return DailyPaperFragment()
        }

    }

}