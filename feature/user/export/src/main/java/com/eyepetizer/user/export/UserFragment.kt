package com.eyepetizer.user.export

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.airbnb.epoxy.EpoxyAsyncUtil
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyViewHolder
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.navigation.navGraphViewModel
import com.eyepetizer.user.export.databinding.FragmentUserBinding
import com.eyepetizer.user.export.models.ItemViewBindingEpoxyHolder_
import com.eyepetizer.user.export.service.ITestPathService
import com.therouter.TheRouter
import com.tongsr.base.mavericks.MavericksFragment
import com.tongsr.common.widget.LoaderMoreView
import com.tongsr.common.widget.LoaderMoreViewModel_
import com.tongsr.common.widget.loaderMoreView
import com.tongsr.core.component.EndlessRecyclerViewScrollListener
import com.tongsr.core.util.LogUtils
import com.tongsr.router.routerNavigation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * @author tongsr
 * @version 1.0
 * @date 2023/5/3
 * @email ujffdtfivkg@gmail.com
 * @description 用户页面
 */

data class UserState(
    val position: Int = 0
) : MavericksState

class UserViewModel(initialState: UserState) : MavericksViewModel<UserState>(initialState) {

    private val userRepository = UserRepository(viewModelScope)

    fun getTextPagingData(): Flow<PagingData<TextModel>> {
        return userRepository.getTextPagingData().cachedIn(viewModelScope)
    }

}


class UserController :
    PagingDataEpoxyController<TextModel>(diffingHandler = EpoxyAsyncUtil.getAsyncBackgroundHandler()) {

    /**
     * 是否正在加载更多
     */
    var isLoading: Boolean = false
        set(value) {
            field = value
            LogUtils.e("触发:$value")
            requestForcedModelBuild()
        }

    override fun buildItemModel(currentPosition: Int, item: TextModel?): EpoxyModel<*> {
        return item?.let {
            ItemViewBindingEpoxyHolder_().id(item.text).title(item.text).listener {
                val testMainPath =
                    TheRouter.get(ITestPathService::class.java)?.getTestMainPath() ?: ""
                routerNavigation(testMainPath)
            }
        } ?: ItemViewBindingEpoxyHolder_().id(-currentPosition)
    }

    override fun addModels(models: List<EpoxyModel<*>>) {
        when {
            isLoading -> {
                loaderMoreView {
                    id("loading")
                    onBind { _, _, _ ->
                        LogUtils.e("loaderMoreView onBind")
                    }
                }
            }
        }
        super.addModels(models)
        LogUtils.e(models.size)

    }

}

class UserFragment : MavericksFragment() {

    private val binding by viewBinding(FragmentUserBinding::bind)
    private val userViewModel by fragmentViewModel(UserViewModel::class)
    private val userController = UserController()

    override fun initData(bundle: Bundle?) {

    }

    override fun onBindLayout(): Int = R.layout.fragment_user

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        binding.epoxyRecyclerView.setController(userController)
    }

    override fun doBusiness() {
        lifecycleScope.launch {
            userViewModel.getTextPagingData().collectLatest {
                userController.submitData(it)
            }
        }
        userController.addLoadStateListener {
            LogUtils.e(it.append.toString(), it.source.append.toString())
            val isLoading = it.source.append is LoadState.Loading

            if (isLoading) {
                // 正在加载中，执行相关操作
                userController.isLoading = true
            } else {
                // 加载完成或加载失败，执行其他操作
            }
        }
    }

    override fun invalidate() {

    }

    // 隐藏加载中样式
    private fun hideLoading() {
        removeCallbacks(hideLoadingRunnable)
        runOnUiThreadDelayed(2000L, hideLoadingRunnable)
    }

    private val hideLoadingRunnable = Runnable {
        userController.isLoading = false
    }

}
