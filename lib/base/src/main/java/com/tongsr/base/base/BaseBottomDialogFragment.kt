package com.tongsr.base.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.*
import com.blankj.utilcode.util.AppUtils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tongsr.core.R
import timber.log.Timber

/**
 * @author Tongsr
 * @version 1.0
 * @date 2022/11/22
 * @email ujffdtfivkg@gmail.com
 * @description BaseBottomDialogFragment
 */
abstract class BaseBottomDialogFragment: BottomSheetDialogFragment(), IBaseView {

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