package com.eyepetizer.found.export

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.eyepetizer.found.export.databinding.FragmentFoundBinding
import com.tongsr.base.base.BaseFragment

/**
 * @author tongsr
 * @version 1.0
 * @date 2023/5/3
 * @email ujffdtfivkg@gmail.com
 * @description 发现页面
 */
class FoundFragment: BaseFragment() {

    private val binding by viewBinding(FragmentFoundBinding::bind)

    override fun initData(bundle: Bundle?) {
        
    }

    override fun onBindLayout(): Int = R.layout.fragment_found

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        
    }

    override fun doBusiness() {
        binding.loadingView.start()
    }

}