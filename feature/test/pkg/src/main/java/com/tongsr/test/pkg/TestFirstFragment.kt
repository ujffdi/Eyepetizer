package com.tongsr.test.pkg

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.eyepetizer.user.export.FakeDataManager
import com.eyepetizer.user.export.models.itemViewBindingEpoxyHolder
import com.tongsr.base.base.BaseFragment
import com.tongsr.core.util.LogUtils
import com.tongsr.test.pkg.databinding.FragmentTestFirstBinding

/**
 * @author tongsr
 * @version 1.0
 * @date 2023/6/4
 * @email ujffdtfivkg@gmail.com
 * @description 测试 fragment 重建后数据保存
 */
class TestFirstFragment : BaseFragment() {

    private val binding by viewBinding(FragmentTestFirstBinding::bind, onViewDestroyed = {

    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController(this)
        binding.epoxyRecyclerView.withModels {
            FakeDataManager.getTextData(1, 50).forEach {
                itemViewBindingEpoxyHolder {
                    id(it.text)
                    title(it.text)
                    listener {
                      navController.navigate(R.id.action_testFirstFragment_to_testSecondFragment)
                    }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        LogUtils.e("onSaveInstanceState")
    }

    override fun initData(bundle: Bundle?) {
        
    }

    override fun onBindLayout(): Int = R.layout.fragment_test_first

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        
    }

    override fun doBusiness() {
        
    }


}