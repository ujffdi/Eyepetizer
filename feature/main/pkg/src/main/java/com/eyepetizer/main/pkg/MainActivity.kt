package com.eyepetizer.main.pkg

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
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
        BarUtils.setStatusBarLightMode(this, true)

    }

    override fun doBusiness() {

    }

    override fun onStart() {
        super.onStart()
        initDslTabLayout()
    }

    private fun initDslTabLayout() {
        val navController = findNavController(R.id.nav_host_fragment)
        val navBuilder =
            NavOptions.Builder().setLaunchSingleTop(true).setRestoreState(true).setPopUpTo(
                navController.graph.findStartDestination().id,
                inclusive = false,
                saveState = true
            )
        binding.dslTab.configTabLayoutConfig {

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
                            switchFragment(navController, selectedView.id, navBuilder.build())
                            selectedView.selected()
                        }
                    }
                }
        }
    }

    /**
     * 切换 Fragment
     *
     * @param navController navController
     * @param id view id
     * @param navOptions NavOptions
     */
    private fun switchFragment(
        navController: NavController, @IdRes id: Int, navOptions: NavOptions
    ) {
        // 使用深度链接，不会保存view的状态。类似于RecyclerView的滚动状态，是不会保存。
        // 和常规的从A到B Fragment，不太相同。这是底部导航栏->平级的Fragment
        //val request = NavDeepLinkRequest.Builder.fromUri(NAV_USER.toUri()).build()
        val actionId = when (id) {
            R.id.tab_home -> R.id.nav_page_home
            R.id.tab_social -> R.id.nav_square
            R.id.tab_discover -> R.id.nav_found
            R.id.tab_mine -> R.id.nav_user
            else -> R.id.nav_page_home
        }
        navController.navigate(actionId, null, navOptions)
    }

}