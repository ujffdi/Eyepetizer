package com.eyepetizer.user.export

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import by.kirich1409.viewbindingdelegate.viewBinding
import com.airbnb.epoxy.EpoxyAsyncUtil
import com.airbnb.epoxy.EpoxyModel
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

    override fun buildItemModel(currentPosition: Int, item: TextModel?): EpoxyModel<*> {
        return item?.let {
            ItemViewBindingEpoxyHolder_().id(item.text).title(item.text).listener {
                val testMainPath = TheRouter.get(ITestPathService::class.java)?.getTestMainPath() ?: ""
                routerNavigation(testMainPath)
            }
        } ?: ItemViewBindingEpoxyHolder_().id(-currentPosition)
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

    }

    override fun invalidate() {

    }

}
