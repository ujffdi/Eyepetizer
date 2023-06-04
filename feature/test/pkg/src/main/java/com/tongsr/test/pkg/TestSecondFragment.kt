package com.tongsr.test.pkg

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.tongsr.test.pkg.databinding.FragmentTestSecondBinding

/**
 * @author tongsr
 * @version 1.0
 * @date 2023/6/4
 * @email ujffdtfivkg@gmail.com
 * @description 测试 fragment 重建后数据保存
 */
class TestSecondFragment : Fragment(R.layout.fragment_test_second) {

    private val binding by viewBinding(FragmentTestSecondBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}