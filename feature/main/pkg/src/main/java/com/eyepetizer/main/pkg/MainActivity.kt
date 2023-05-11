package com.eyepetizer.main.pkg

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.eyepetizer.main.export.PATH_MAIN
import com.eyepetizer.main.pkg.databinding.ActivityMainBinding
import com.eyepetizer.main.pkg.weiget.TabView
import com.therouter.router.Route
import com.tongsr.base.base.BaseActivity
import com.tongsr.core.util.BarUtils
import com.tongsr.core.util.ToastUtils


/**
 * @author tongsr
 * @version 1.0
 * @date 2023/5/3
 * @email ujffdtfivkg@gmail.com
 * @description Main
 */
@Route(path = PATH_MAIN, description = "main")
class MainActivity : BaseActivity() {

    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun initData(bundle: Bundle?) {
        BarUtils.setStatusBarLightMode(parentActivity, false)
    }

    override fun onBindLayout(): Int = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        initDslTabLayout()
    }

    override fun doBusiness() {

    }

    private fun initDslTabLayout() {
        binding.tabLayout.configTabLayoutConfig {

            onSelectItemView = { _, index, _, _ ->
                if (index == 2) {
                    //拦截
                    ToastUtils.showShort("发布")
                    true
                } else {
                    false
                }
            }

            onSelectViewChange =
                { fromView: View?, selectViewList: List<View>, reselect: Boolean, _: Boolean ->
                    if (reselect.not()) {
                        if (fromView is TabView) {
                            fromView.unselected()
                        }
                        val selectedView = selectViewList.first()
                        if (selectedView is TabView) {
                            selectedView.selected()
                        }
                    }
                }
        }
    }

}