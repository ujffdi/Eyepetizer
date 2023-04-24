package com.tongsr.base.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDialogFragment
import com.tongsr.base.R

import com.tongsr.core.util.AppUtils
import com.tongsr.core.util.ScreenUtils
import timber.log.Timber

/**
 * @author Tongsr
 * @version 1.0
 * @date 2022/11/21
 * @email ujffdtfivkg@gmail.com
 * @description BaseDialogFragment
 */
abstract class BaseDialogFragment : AppCompatDialogFragment(), IBaseView {

    /*
        解决DialogFragment宽度问题有以下方案：
        1.XML嵌套一层布局（不推荐）
        2.调用dialog?.window?.setLayout(-1,-2)
        3.拿到window的LayoutParams对宽高进行修改

        本例子采用第三种方法
        val dialog = TestDialog.newInstance().apply {
                mDimAmount = 0F
                mGravity = Gravity.BOTTOM
                ...
        }
        dialog.show(supportFragmentManager, "test")
     */

    protected lateinit var parentActivity: Activity
    protected lateinit var appContext: Context
    protected lateinit var contentView: View
    protected lateinit var inflater: LayoutInflater

    private var isDebug = true
    /**
     * 默认竪屏3/4的屏幕宽度，橫屏狀態300dp
     */
    var dialogWidth: Int = (ScreenUtils.getAppScreenWidth() * 0.75).toInt()

    /**
     * 默认自适应高度
     */
    var dialogHeight: Int = ViewGroup.LayoutParams.WRAP_CONTENT

    /**
     * 默认居中
     */
    var dialogGravity: Int = Gravity.CENTER

    /**
     * 显示和隐藏动画
     */
    var dialogWindowAnimations: Int = R.style.DialogDefaultAnimation

    /**
     * 窗口默认半透明
     */
    var dialogDimAmount: Float = 0.6f

    /**
     * 背景默认不透明
     */
    var dialogAlpha: Float = 1f

    override fun onAttach(context: Context) {
        log("onAttach")
        super.onAttach(context)
        parentActivity = context as Activity
        appContext = context
        setStyle(STYLE_NO_TITLE, R.style.BaseDialogTheme)
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
            params.width = dialogWidth
            params.height = dialogHeight
            params.gravity = dialogGravity
            params.windowAnimations = dialogWindowAnimations
            params.dimAmount = dialogDimAmount
            params.alpha = dialogAlpha
            it.attributes = params
        }
    }

    override fun onResume() {
        log("onResume")
        super.onResume()
    }

    override fun onStop() {
        log("onStop")
        super.onStop()
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
        private const val TAG = "DIALOG_FRAGMENT_LIFE"
    }

}