package com.eyepetizer.home.export

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.eyepetizer.home.export.databinding.FragmentHomeBinding
import com.tongsr.base.base.BaseFragment

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
        
    }

    override fun doBusiness() {
        binding.home.setOnClickListener {
            viewModel.incrementCount()
        }
    }

    override fun invalidate() = withState(viewModel) { state ->
        binding.home.text = "Count: ${state.count}"
    }

}
