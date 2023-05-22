package com.eyepetizer.user.export

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.eyepetizer.user.export.databinding.FragmentUserBinding
import com.tongsr.base.base.BaseFragment

/**
 * @author tongsr
 * @version 1.0
 * @date 2023/5/3
 * @email ujffdtfivkg@gmail.com
 * @description 用户页面
 */

data class UserState(
    val textData: Async<List<TextBtnData>> = Uninitialized,
    val btnData: Async<List<TextBtnData>> = Uninitialized
) : MavericksState

class UserViewModel(initialState: UserState) : MavericksViewModel<UserState>(initialState) {

    init {
        getTextData()
        getBtnData()
    }

    fun getTextData() {
        suspend {
            FakeDataManager.getTestTextData()
        }.execute { copy(btnData = it) }
    }

    fun getBtnData() {
        suspend {
            FakeDataManager.getTestBtnData()
        }.execute { copy(textData = it) }
    }

}

class UserFragment : BaseFragment(), MavericksView {

    private val binding by viewBinding(FragmentUserBinding::bind)
    private val viewModel: UserViewModel by fragmentViewModel()

    override fun initData(bundle: Bundle?) {

    }

    override fun onBindLayout(): Int = R.layout.fragment_user

    override fun initView(savedInstanceState: Bundle?, contentView: View) {

    }

    override fun doBusiness() {
//        binding.epoxyRecyclerView.withModels {
//            FakeDataManager.getTestTextData().forEachIndexed { index, textBtnData ->
//                textLayout {
//                    id("text $index")
//                    text(textBtnData.text)
//                }
//            }
//            FakeDataManager.getTestBtnData().forEachIndexed { index, textBtnData ->
//                buttonLayout {
//                    id("btn $index")
//                    text(textBtnData.text)
//                }
//            }
//        }
    }

    override fun invalidate() = withState(viewModel) { state ->
        binding.epoxyRecyclerView.withModels {
            state.btnData.invoke()?.forEachIndexed { index, textBtnData ->
                textLayout {
                    id("text $index")
                    text(textBtnData.text)
                }
            }
            state.btnData.invoke()?.forEachIndexed { index, textBtnData ->
                buttonLayout {
                    id("btn $index")
                    text(textBtnData.text)
                }
            }
        }
    }

}