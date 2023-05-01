package com.tongsr.base.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tongsr.base.R
import com.tongsr.core.util.AppUtils
import timber.log.Timber


/**
 * @author Tongsr
 * @version 1.0
 * @date 2022/11/22
 * @email ujffdtfivkg@gmail.com
 * @description BaseBottomDialogFragment。使用 BaseDialogFragment 设置动画显示在底部几乎相等的功能
 */
abstract class BaseBottomDialogFragment : BottomSheetDialogFragment(), IBaseView {

    protected lateinit var parentActivity: Activity
    protected lateinit var appContext: Context
    protected lateinit var inflater: LayoutInflater
    protected lateinit var contentView: View

    private var isDebug = true

    /**
     * 窗口默认半透明
     */
    var dialogDimAmount: Float = 0.6f

    override fun onAttach(context: Context) {
        log("onAttach")
        super.onAttach(context)
        parentActivity = context as Activity
        appContext = context
        setStyle(STYLE_NORMAL, R.style.BaseBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        log("onCreateView")

        // 解决高度问题
        dialog?.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet =
                d.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            if (bottomSheet != null) {
                val coordinatorLayout = bottomSheet.parent as CoordinatorLayout
                val bottomSheetBehavior: BottomSheetBehavior<*> =
                    BottomSheetBehavior.from(bottomSheet)
                bottomSheetBehavior.peekHeight = bottomSheet.height
                coordinatorLayout.parent.requestLayout()
            }
        }

        this.inflater = inflater
        setContentView()
        initView(savedInstanceState, contentView)
        return contentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        log("onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        initData(arguments)
        doBusiness()
    }

    override fun onStart() {
        log("onStart")
        super.onStart()
        dialog?.window?.let {
            val params: WindowManager.LayoutParams = it.attributes
            params.dimAmount = dialogDimAmount
            it.attributes = params
        }
    }

    override fun onResume() {
        log("onResume")
        super.onResume()
    }

    override fun onDestroyView() {
        log("onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        log("onDestroy")
        super.onDestroy()
    }

    override fun setContentView() {
        if (onBindLayout() <= 0) {
            return
        }
        contentView = inflater.inflate(onBindLayout(), null)
    }

    /**
     * 禁止点击外部使dialog消失
     */
    open fun setTouchExNoDismiss() {
        dialog?.let {
            it.setCancelable(false)
            it.setCanceledOnTouchOutside(false)
        }
    }

    /**
     * 禁止点击返回键使dialog消失
     */
    open fun setBackNoDismiss() {
        dialog?.let {
            it.setOnKeyListener { _, keyCode, _ ->
                keyCode == KeyEvent.KEYCODE_BACK
            }
        }
    }

    /**
     * 禁止点击外部和返回键使dialog消失
     */
    open fun setTouchExAndBackNoDismiss() {
        dialog?.let {
            it.setCancelable(false)
            it.setCanceledOnTouchOutside(false)
            it.setOnKeyListener { _, keyCode, _ ->
                keyCode == KeyEvent.KEYCODE_BACK
            }
        }
    }

    protected open fun log(msg: String) {
        isDebug = AppUtils.isAppDebug()
        if (isDebug) {
            Timber.tag(TAG).d("${javaClass.simpleName}: $msg")
        }
    }

    companion object {
        private const val TAG = "BOTTOM_DIALOG_FRAGMENT_LIFE"
    }

}