package com.tongsr.base.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tongsr.core.util.AppUtils
import timber.log.Timber


/**
 * @author Tongsr
 * @version 1.0
 * @date 2022/11/21
 * @email ujffdtfivkg@gmail.com
 * @description BaseFragment
 */
abstract class BaseFragment : Fragment(), IBaseView {

    protected lateinit var parentActivity: Activity
    protected lateinit var appContext: Context
    protected lateinit var inflater: LayoutInflater
    protected lateinit var contentView: View
    private var isLoaded = false
    private var isDebug = true

    override fun onAttach(context: Context) {
        log("onAttach")
        super.onAttach(context)
        parentActivity = context as Activity
        appContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        log("onCreate")
        super.onCreate(savedInstanceState)
        initData(arguments)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        log("onCreateView")
        this.inflater = inflater
        setContentView()
        return contentView
    }

    override fun setContentView() {
        if (onBindLayout() <= 0) {
            return
        }
        contentView = inflater.inflate(onBindLayout(), null)
    }

    override fun onStart() {
        log("onStart")
        super.onStart()
    }

    override fun onResume() {
        log("onResume")
        super.onResume()
        if (!isLoaded && !isHidden) {
            onLazyInitView()
            isLoaded = true
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        log("onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        initView(savedInstanceState, contentView)
        doBusiness()
    }

    override fun onStop() {
        log("onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        log("onDestroyView")
        super.onDestroyView()
        isLoaded = false
    }

    override fun onDestroy() {
        log("onDestroy")
        super.onDestroy()
    }

    /**
     * 懒加载
     */
    protected open fun onLazyInitView() {

    }

    protected open fun log(msg: String) {
        isDebug = AppUtils.isAppDebug()
        if (isDebug) {
            Timber.tag(TAG).d("${javaClass.simpleName}: $msg")
        }
    }

    companion object {
        private const val TAG = "FRAGMENT_LIFE"
    }

}